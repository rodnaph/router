
# Routes Helper [![Build Status](https://secure.travis-ci.org/rodnaph/router.png?branch=master)](http://travis-ci.org/rodnaph/router) [![Dependencies Status](http://clj-deps.herokuapp.com/github/rodnaph/router/status.png)](http://clj-deps.herokuapp.com/github/rodnaph/router)

Router aims to allow you to store route definitions in one place,
and then use these routes with application tools like Compojure, 
and right through to your templating.

## Usage

Router is available from [Clojars](https://clojars.org/router). To
define some routes...

```clojure
(ns my.namespace
  (:use router.core))

;; First define your routes

(set-routes!
  {:home            "/"
   :blog            "/blog"
   :blog.post       "/blog/:id"})

;; Then you can use them with Compojure

(defroutes app-routes
  (GET (rte :home) [] "Show homepage"))
```

### Fetching URLs

You can then use the _url_ function to fetch parsed routes as URLs.

```clojure
(url :blog.post
     :id 123) ; => "/blog/123"
```

### Enlive

There is also support for using your routes with Enlive, via transformers.

```clojure
(deftemplate bazzle "file.html"
  []
  ;; replace a form action
  [:form] (action :blog.post.comment
                  :id 123)
  ;; Or set an anchor URL
  [:a] (href :home))
```

These two helpers use the function _route-attr_ which you can use to
add arbitrary attributes to nodes.

## ClojureScript

Router is also usable in ClojureScript, just specify it as a crossover namespace
with cljsbuild.  You can then share your routes between the server and
the client with exactly the same code.


