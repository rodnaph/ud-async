
(ns ud-async.core
  (:refer-clojure :exclude [map filter distinct remove])
  (:require [cljs.core.async :refer [>! <! chan put! take! timeout close!]]
            [goog.dom :as dom]
            [leaf.core :refer [log]])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]))

