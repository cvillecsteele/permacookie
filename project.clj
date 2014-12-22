(defproject org.clojars.cvillecsteele/ring-permacookie-middleware
  "1.4.0"
  :url "https://github.com/cvillecsteele/permacookie"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :description "Ring middleware for planting visitor cookies."
  :dev-dependencies [[lein-clojars "0.6.0"]
                     [compojure "0.5.2"]
                     [ring/ring-core "0.3.1"]
                     [ring/ring-jetty-adapter "0.3.1"]
                     [lein-run "1.0.0-SNAPSHOT"]]
  :repositories {"clojars" "http://clojars.org/repo"}
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]])
