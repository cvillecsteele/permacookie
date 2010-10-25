# permacookie

Sends a cookie to the browser that will "permanently" identify that
visitor.

## Usage

Defines a ring middleware function at
ring.middleware.permacookie/wrap-permacookie.

Your usage might resemble:

      (ns mine.core
        (:use [compojure.core]
            [ring.middleware.cookies]
            [ring.middleware.permacookie]))
      (defroutes webservice
        (GET          "/events"               [] events/render))
      (def app (-> (var webservice)
                   (middle/wrap-visitor-permacookie {:name "nifty"})
                   wrap-cookies))`

## Installation

For lein, in your project.clj:

[org.clojars.cvillecsteele/ring-permacookie "1.0.0-SNAPSHOT"]

## License

Copyright (C) 2010 Colin Steele (colin@colinsteele.org)

Distributed under the Eclipse Public License, the same as Clojure.
