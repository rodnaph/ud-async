
(ns ud-async.macros)

(defmacro run [& body]
  `(do ~@body))

