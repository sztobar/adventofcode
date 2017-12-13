(ns adventofcode2017.day12-test
  (:require [adventofcode2017.day12 :as d12]
            [clojure.test :refer :all]))


(deftest ex1

  (testing "counting connections"
    (is (= (d12/ex1 (d12/get-input "2017/day12_test.data"))
           6))))


(deftest ex2

  (testing "counting groups"
    (is (= (d12/ex2 (d12/get-input "2017/day12_test.data"))
           2))))

