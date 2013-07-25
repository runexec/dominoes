(defproject dominoes "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/google-closure-library-third-party "0.0-2029"]
                 [org.clojure/clojurescript "0.0-1847"]
                 [domina "1.0.0"]]
  :source-paths ["src/clj"]
  :hooks [leiningen.cljsbuild]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :clojurescript? true
  :cljsbuild {:builds 
              [{:jar true
                :source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/dominoes.js"
                           :optimizations :whitespace
                           :warnings true
                           :pretty-print false}}]})
                         
