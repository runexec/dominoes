(ns dominoes
  (:use [domina 
         :only [by-id
                value
                set-text!
                set-styles!]]
        [domina.events
         :only [listen!]]))


(def ^:dynamic *valid* (atom false))

(def ^:dynamic *required* (atom []))
 
(def ^:dynamic *error-style*
  (atom
   {:max-width "200px"
    :display "block"
    :visibility "visible"
    :color "red"
    :background-color "#FFFCCC"
    :border-color "#FF3333"
    :border-width "2px"
    :border-top-style "solid"
    :border-bottom-style "solid"
    :padding "2px"
    :margin "2px"}))

(def ^:dynamic *error-remove-style* (atom {:visibility "hidden"}))

(defn document-ready? []
  (and js/document
       (.. js/document -getElementById)))

(defn error-message!
  [label-id msg]
  (let [obj (by-id label-id)]
    (set-styles! obj @*error-style*)
    (set-text! obj msg)))

(defn remove-error! [label-id]
  (let [obj (by-id label-id)]
    (set-styles! obj @*error-remove-style*)
    (set-text! obj "")))

(defn validate! 
  [object-id
   error-label-id
   & validations-coll]
  (swap! *required* conj object-id)
  (doseq [_ validations-coll
          :let [[event-kw & tests] _]]
    (listen! (by-id object-id)
             (keyword event-kw)
             (fn [evt]
               (let [obj (by-id object-id)
                     obj-val (value obj)]
                 (remove-error! error-label-id)
                 (reset! *valid* true)
                 (doseq [[error-msg test-fn] (partition 2 tests)]
                   (when (-> obj-val test-fn false?)
                     (reset! *valid* false)
                     (error-message! error-label-id
                                    error-msg))))))))

(defn valid? []
  (and @*valid*
       (every? #(not
                 (or (= % "")
                     (nil? %)))
               (map #(-> % by-id value)
                    @*required*))))

(defn valid-submit!
  [button-id
   button-error-id
   invalid-msg
   valid-fn]
  (listen! (by-id button-id)
           :click ;; onClick
           (fn [evt]
             (if-not (valid?)
               (error-message! button-error-id
                              invalid-msg)
               (valid-fn)))))



