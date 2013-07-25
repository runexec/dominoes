# dominoes

A ClojureScript validation library


[Click here to try it live](https://rawgithub.com/runexec/dominoes/master/example/index.html) or watch the animated GIF preview (loading)

![preview gif animation](dominoes.gif "Dominoes animated GIF")

## Usage Example


```clojure
(use '[dominoes.core
       :only [document-ready?
              validate!
              valid-submit!
              *error-style*]])

(defn init []
  (when (document-ready?)

    ;; Update error style
    ;; You can also update *error-remove-style*

    (swap! *error-style* assoc :color "red")

    ;; validate object id
    (validate! "name-input"
               ;; error message label object id
               "name-error" 
               ;; [event-kw test-fn error-msg test-fn err etc...]
               ;; validate! accepts mutliple events
               [:click
                "Does nothing"
                #(or % true)] 
               ;; Empty or not, the following must be valid
               ;; and checked while typing
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
    
    ;; Enforce a valid submission
    ;; submit button object id
    (valid-submit! "submit-button"
                   ;; error message label object id
                   "submit-error"
                   ;; error message
                   "The form input is invalid!"
                   ;; do on valid
                   (fn [] (.. js/document (write "Form is valid!"))))))

(set! (.. js/window -onload) init)
```

An example HTML file to go with the example ClojureScript above.


```
<html>
  <head>
    <script type="text/javascript">
      var CLOSURE_NO_DEPS = true;
    </script>
  </head>
  <body>

    <noscript><h1>JavaScript is required to view this page.</h1></noscript>

    <fieldset>

      <label>Name Input</label>
      <br />
      <label id="name-error"></label>
      <p>
	<input type="text" id="name-input" />
      </p>

      <button id="submit-button">Submit</button>
      <br />
      <label id="submit-error"></label>

    </fieldset>

  </body>
  <script src="js/dominoes.js"></script>
</html>
```

## Install

```bash
git clone https://github.com/runexec/dominoes.git
cd dominoes; lein do uberjar, install
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
