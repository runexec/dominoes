(ns example.core
  (:use [dominoes
         :only [validate!
                valid-submit!]]))


(defn init []
  ;; validate object id
  (validate! "name-input"
             ;; error message label object id
             "name-error" 
             ;; [event-kw test-fn error-msg test-fn err etc...]
             [:blur ;; onBlur
              "Can't be empty!"
              #(not= 0 (count %))]
             ;; Empty or not, the following must be valid
             [:keyup ;; onKeyUp
              "Name can only contain letters and numbers"
              #(< 0 (count
                     (.. (str %)
                         (match 
                          (js/RegExp. "^\\w+$")))))
              "Name must be at least 3 chars long"
              #(<= 3 (count %))
              "Name must not contain a space"
              #(not (.. (str %) (contains " ")))])
  
  ;; submit button object id
  (valid-submit! "submit-button"
                 ;; error message label object id
                 "submit-error"
                 ;; error message
                 "The form input is invalid!"
                 ;; do on valid
                 (fn [] (.. js/document (write "Form is valid!")))))

(set! (.. js/window -onload) init)
                
