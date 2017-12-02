(ns adventofcode.seventh-test
  (:require [clojure.test :refer :all]
            [adventofcode.seventh :refer :all]))

(defn long-str [& strings] (clojure.string/join "\n" strings))

(def s
  (long-string "123 -> x"
               "456 -> y"
               "x AND y -> d"
               "x OR y -> e"
               "x LSHIFT 2 -> f"
               "y RSHIFT 2 -> g"
               "NOT x -> h"
               "NOT y -> i"))

(deftest a-test
  (testing "circuit"
    (let [c (read-signals s)]
      (is (= (:d c) 72))
      (is (= (:e c) 507))
      (is (= (:f c) 492))
      (is (= (:g c) 114))
      (is (= (:h c) 65412))
      (is (= (:i c) 65079))
      (is (= (:x c) 123))
      (is (= (:y c) 456)))))
