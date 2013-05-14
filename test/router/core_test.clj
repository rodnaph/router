
(ns router.core-test
  (:require [clojure.test :refer :all]
            [router.core :refer :all]))

(set-routes!
  {:foo          "/foo"
   :foo.id       "/foo/:id"})

(deftest routes-can-be-fetched-raw
  (is (= "/foo" (rte :foo)))
  (is (= "/foo/:id" (rte :foo.id))))

(deftest routes-can-be-fetch-with-vars-replaced
  (is (= "/foo/:id" (url :foo.id)))
  (is (= "/foo/1" (url :foo.id :id 1))))

(deftest nodes-can-have-attributes-set
  (is (= {:attrs {:foo "/foo/1"}}
         ((route-attr :foo :foo.id :id 1) {}))))

