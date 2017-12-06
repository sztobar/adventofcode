(ns adventofcode2017.day2-test
  (:require [adventofcode2017.day2 :as d2]
            [clojure.test :refer :all]
            [clojure.string :as str]))

(def test-ex1-input
  (str/join "\n" ["5 1 9 5"
                  "7 5 3"
                  "2 4 6 8"]))

(deftest ex1-test
  (testing "ex1"
    (is (= (d2/ex1 test-ex1-input) 18))))


(def test-ex2-input
  (str/join "\n" ["5 9 2 8"
                  "9 4 7 3"
                  "3 8 6 5"]))

(deftest ex2-test
  (testing "ex2"
    (is (= (d2/ex2 test-ex2-input) 9))))
