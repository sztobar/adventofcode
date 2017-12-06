(ns adventofcode2015.fifth-test
  (:require [clojure.test :refer :all]
            [adventofcode2015.fifth :refer :all]))

(deftest a-test
  (testing "Nice string checker"
    (is (= (is-nice "ugknbfddgicrmopn") true))
    (is (= (is-nice "aaa") true))
    (is (= (is-nice "jchzalrnumimnmhp") false))
    (is (= (is-nice "haegwjzuvuyypxyu") false))
    (is (= (is-nice "dvszwmarrgswjxmb") false)))

  (testing "Nice string checker 2"
    (is (= (is-nice2 "qjhvhtzxzqqjkmpb") true))
    (is (= (is-nice2 "xxyxx") true))
    (is (= (is-nice2 "uurcxstgmygtbstg") false))
    (is (= (is-nice2 "ieodomkazucvgmuy") false))))
