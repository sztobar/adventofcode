(ns adventofcode2017.day6-test
  (:require [adventofcode2017.day6 :as d6]
            [clojure.test :refer :all]))


(deftest ex1-test

  (testing "[0 2 7 0]"
    (is (= (d6/next-step [0 2 7 0])
           [2 4 1 2])))

  (testing "[2 4 1 2]"
    (is (= (d6/next-step [2 4 1 2])
           [3 1 2 3])))

  (testing "[3 1 2 3]"
    (is (= (d6/next-step [3 1 2 3])
           [0 2 3 4])))

  (testing "[0 2 3 4]"
    (is (= (d6/next-step [0 2 3 4])
           [1 3 4 1])))

  (testing "[1 3 4 1]"
    (is (= (d6/next-step [1 3 4 1])
           [2 4 1 2])))

  (testing "cycle [0 2 7 0]"
    (is (= (d6/ex1 [0 2 7 0])
           5))))


(deftest ex2-test

  (testing "cycle [0 2 7 0]"
    (is (= (d6/ex2 [0 2 7 0])
           4))))
