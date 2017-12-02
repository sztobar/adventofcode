(ns adventofcode.first-test
  (:require [clojure.test :refer :all]
            [adventofcode.first :refer :all]))

(deftest a-test
  (testing "Santa went to floor '0'"
    (is (= (santa-go "(())") 0))
    (is (= (santa-go "()()") 0)))
  
  (testing "Santa went to floor '3'"
    (is (= (santa-go "(((") 3))
    (is (= (santa-go "(()(()(") 3)))

  (testing "Santa went to floor '3'"
    (is (= (santa-go "))(((((") 3)))

  (testing "Santa went to floor '-1'"
    (is (= (santa-go "())") -1))
    (is (= (santa-go "))(") -1)))

  (testing "Santa went to floor '-3'"
    (is (= (santa-go ")))") -3))
    (is (= (santa-go ")())())") -3))))
