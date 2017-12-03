(ns adventofcode2017.day3-test
  (:require [adventofcode2017.day3 :as d3]
            [clojure.test :refer :all]))

(deftest ex1-test

  (testing "1 is 0 steps"
    (is (= (d3/ex1 1) 0)))

  (testing "12 is 3 steps"
    (is (= (d3/ex1 12) 3)))

  (testing "23 is 2 steps"
    (is (= (d3/ex1 23) 2)))

  #_(testing "1024 is 31 steps"
      (is (= (d3/ex1 1024) 31)))

  (testing "101 is 10 steps"
    (is (= (d3/ex1 101) 10))))


(deftest ex2-test

  (testing "1 has next greater value 2"
    (is (= (d3/ex2 1) 2)))

  (testing "3 has next greater value 4"
    (is (= (d3/ex2 3) 4)))

  (testing "4 has next greater value 5"
    (is (= (d3/ex2 4) 5)))

  (testing "14 has next greater value 23"
    (is (= (d3/ex2 14) 23))))
