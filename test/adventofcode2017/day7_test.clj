(ns adventofcode2017.day7-test
  (:require [clojure.test :refer :all]
            [adventofcode2017.day7 :as d7]
            [clojure.java.io :as io]))


(def test-filename "2017/day7_test.data")

(defn get-test-input []
  (d7/get-input test-filename))


(deftest ex1-test

  (testing "tree top"
    (is (= (d7/get-root-name (get-test-input))
           "tknk"))))


(deftest ex2-test

  (testing "calculate balance for umgl"
    (is (= (d7/ex2 test-filename)
           60)))

