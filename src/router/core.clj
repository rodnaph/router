
(ns router.core
  (:require [clojure.string :as s])
  (:import (java.net URLEncoder)))

(def ^{:dynamic true :private true}
  routes
  (atom {}))

;; Helpers
;; -------

(defn- map-replace [m text]
  (reduce
    (fn [acc [k v]]
      (s/replace acc (str k) (URLEncoder/encode (str v))))
    text m))

;; Public
;; ------

(defn set-routes! [routes*]
  (reset! routes routes*))

(defn rte [id]
  (id @routes))

(defn url
  "Resolves a route name to its expanded value"
  [id & params]
  (map-replace
    (apply hash-map params)
    (rte id)))

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

