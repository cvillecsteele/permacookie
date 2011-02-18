(ns ring.middleware.permacookie
  (:require [clojure.contrib.except :as except])
  (:import (java.util UUID)))

(defn permacookie
  "Cough up a map of permacookie values."
  [value expires path domain]
  (if (or (= "127.0.0.1" domain) (= "localhost" domain))
    {:value value :expires expires :path path}
    {:value value :expires expires :path path :domain domain}))

(defn wrap-permacookie
  "Plant a permacookie on the browser."
  ([handler] (wrap-permacookie handler {}))
  ([handler {:keys [name idfn expfn pathfn domainfn]
             :or {name "permacookie"
                  idfn (fn [r] (str (. UUID randomUUID)))
                  expfn (fn [r] "Sun, 01-Jan-2038 00:00:00 GMT")
                  pathfn (fn [r] "/")
                  domainfn (fn [r] (:server-name r))}
             :as opts}]
     (fn [request]
       (if (nil? (:cookies request))
         (except/throwf "No :cookies set on request."))
       (let [cookies (:cookies request)
             old-cookie (get cookies name)
             visitor-id (if old-cookie (:value old-cookie) (idfn request))
             new-cookie (permacookie visitor-id
                                     (expfn request)
                                     (pathfn request)
                                     (domainfn request))]
         (if old-cookie
           ;; don't need to set cookie cuz it's already here
           (handler (assoc request :visitor-id visitor-id))
           ;; set it
           (let [resp (handler (assoc request :visitor-id visitor-id))]
             (assoc resp :cookies (merge
                                   (:cookies resp)
                                   {name new-cookie}))))))))
  

