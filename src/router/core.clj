
(ns router.core
  (:require [clojure.string :as s])
  (:import (java.net URLEncoder)))

(def ^{:dynamic true :private true}
  routes
  (atom {}))

;; Helpers
;; -------

(defn encode [part]
  (URLEncoder/encode part))

(defn- map-replace [params text]
  (reduce
    (fn [acc [k v]]
      (s/replace
        acc
        (str k)
        (encode (str v))))
    text params))

(defn- route-params
  "Return params from route as keywords"
  [route]
  (map (comp keyword second)
       (re-seq #":(\w+)" route)))

(defn- query-params [route params]
  (apply dissoc params (route-params route)))

(defn- query-pair [[k v]]
  (format "%s=%s"
          (encode (name k))
          (encode (str v))))

(defn- query-string [route params]
  (->> (query-params route params)
       (map query-pair)
       (s/join "&")))

;; Public
;; ------

(defn set-routes! [routes*]
  (reset! routes routes*))

(defn rte [id]
  (id @routes))

(defn url
  "Resolves a route name to its expanded value"
  [id & args]
  (let [params (apply hash-map args)
        resource (map-replace params (rte id))
        query (query-string (rte id) params)]
    (str
      resource
      (if (> (count query) 0)
        (str "?" query)))))

;; Enlive Transformers
;; -------------------

(defn route-attr
  "Enlive transformer for setting the href attribute of a node"
  [attr & params]
  (fn [node]
    (assoc-in
      node
      [:attrs attr]
      (apply url params))))

(def href
  "Enlive transformer for setting the href attribute of a node"
  (partial route-attr :href))

(def action
  "Transformer for the action attribute"
  (partial route-attr :action))

(def src
  "Transformer for the src attribute"
  (partial route-attr :src))

