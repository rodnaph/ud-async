
(defproject ud-async "0.1.0-SNAPSHOT"
  :description "core.async talk for Unified Diff"
  :jvm-opts ^:replace ["-Xmx512m" "-server"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1934"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 [leaf "0.0.2"]]

  :plugins [[lein-cljsbuild "0.3.3"]]

  :cljsbuild
  {:builds
   [{:id "ud-async"
     :source-paths ["src/ud_async"]
     :compiler {:optimizations :none
                :pretty-print false
                :output-dir "out"
                :output-to "main.js"}}]})

