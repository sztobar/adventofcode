(ns adventofcode2017.day5-test
  (:require [adventofcode2017.day5 :as d5]
            [clojure.test :refer :all]))


(deftest ex1-test

  (testing "[0 3 0 1 -3] takes 5 steps"
    (is (= (d5/start-jumps [0 3 0 1 -3]) 5))))


(deftest ex2-test

  (testing "[0 3 0 1 -3] takes 10 steps"
    (is (= (d5/start-strange-jumps [0 3 0 1 -3]) 10))))
