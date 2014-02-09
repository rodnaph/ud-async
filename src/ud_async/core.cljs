
(ns ud-async.core
  (:refer-clojure :exclude [map filter distinct remove])
  (:require [cljs.core.async :refer [>! <! chan put! take! timeout close!]]
            [goog.dom :as dom]
            [goog.events :as evts]
            [goog.net.XhrIo :as XhrIo]
            [leaf.core :refer [log]])
  (:require-macros [cljs.core.async.macros :refer [go alt! go-loop]]))

;; standard async
;; --------------

#_(log "Hello, Unified Diff!")

#_(log (chan))

#_(log (put! (chan) :abc))

#_(log (take! (chan) log))

#_(let [c (chan)]
  (put! c "Something Nice")
  (take! c log))

;; Go Blocks
;; ---------

#_(log (<! (chan)))

#_(log (>! (chan)))

#_(go (log (<! (chan))))

#_(let [c (chan)]
  (go
    (>! c "Channel operations block!")
    (log (<! c))))

#_(let [c (chan)]
  (go
    (>! c "Can anyone hear me?"))
  (go
    (log (<! c))))

#_(let [c (chan)]
  (go
    (log (<! c)))
  (go
    (>! c "Does order matter?")))

#_(let [c (chan)]
  (go
    (log "Knock knock.")
    (log (<! c))
    (log "Doctor who!"))
  (go
    (log "Who's there?")
    (>! c "Doctor.")))

;; Event Handling
;; --------------

(defn events [type el]
  (let [out (chan)]
    (.addEventListener el type
      #(put! out %))
    out))

#_(let [c (events "mousemove" js/window)]
  (go
    (while true
      (log (<! c)))))

;; Sequence Operations
;; -------------------

(defn map [f in]
  (let [out (chan)]
    (go
      (while true
        (>! out (f (<! in)))))
    out))

(defn ->coords [evt]
  [(.-clientX evt)
   (.-clientY evt)])

#_(let [c (map ->coords (events "mousemove" js/window))]
  (go
    (while true
      (log (pr-str (<! c))))))

;; Timeouts
;; --------

#_(go
  (log "Goooooooo!")
  (<! (timeout 3000))
  (log "And relax :)"))

;; Ajax
;; ----

(defn ajax [url]
  (let [out (chan)
        xhr (goog.net.XhrIo.)
        cb #(put! out %)]
    (.send xhr url)
    (goog.events/listen xhr "complete" cb)
    out))

#_(go
  (log "Do some ajax...")
  (let [url "http://api.icndb.com"
        res (<! (ajax url))]
    (log "done! got:" res)))

