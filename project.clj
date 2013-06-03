
(defproject rodnaph/router "0.4.0"
  :description "Routes Helper"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :cljsbuild
    {:builds []
     :crossovers [router.core]
     :crossover-jar true
     :jar true})

