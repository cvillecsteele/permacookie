(ns ring.middleware.permacookie-test
  (:use clojure.test
        ring.middleware.permacookie))

(deftest test-wrapped-permacookie
  (let [app (wrap-permacookie (fn [req] {:status 200 :body "content"}))
        resp (app {:cookies {}})
        cookies (:cookies resp)
        pc (get cookies "permacookie")]
    (is (= 200 (:status resp)))
    (is (map? cookies))
    (is (map? pc))
    (is (not (nil? (:value pc))))
    (is (= "/" (:path pc)))
    (is (= "Sun, 01-Jan-2038 00:00:00 GMT" (:expires pc)))
    (is (nil? (:domain pc)))
    (is (= "content" (:body resp)))))

(deftest test-wrapped-permacookie-existing
  (let [app (wrap-permacookie (fn [req]
                                (is (= "ID" (:visitor-id req)))
                                {:status 200 :body "content"}))
        resp (app {:cookies {"permacookie" {:value "ID"}}})
        cookies (:cookies resp)
        pc (get cookies "permacookie")]
    (is (= 200 (:status resp)))
    (is (nil? cookies))
    (is (nil? pc))
    (is (= "content" (:body resp)))))

(deftest test-wrapped-permacookie-fns
  (let [app (wrap-permacookie (fn [req]
                                (is (not (nil? (:visitor-id req))))
                                {:status 200 :body "content"})
                              {:name "foo"
                               :idfn (fn [r] "ID")
                               :pathfn (fn [r] "path")
                               :expfn (fn [r] "sometime")})
        resp (app {:cookies {}})
        cookies (:cookies resp)
        pc (get cookies "foo")]
    (is (= 200 (:status resp)))
    (is (map? cookies))
    (is (map? pc))
    (is (= "ID" (:value pc)))
    (is (= "path" (:path pc)))
    (is (= "sometime" (:expires pc)))
    (is (nil? (:domain pc)))
    (is (= "content" (:body resp)))))
