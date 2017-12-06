(ns adventofcode2017.day1-test
  (:require [adventofcode2017.day1 :as d1]
            [clojure.test :refer :all]))

(deftest ex1-test

  (testing "1122 = 3"
    (is (= (d1/ex1 "1122") 3)))

  (testing "1111 = 4"
    (is (= (d1/ex1 "1111") 4)))

  (testing "1234 = 0"
    (is (= (d1/ex1 "1234") 0)))

  (testing "91212129"
    (is (= (d1/ex1 "91212129") 9))))


(deftest ex1-test

  (testing "1212 = 6"
    (is (= (d1/ex2 "1212") 6)))

  (testing "1221 = 0"
    (is (= (d1/ex2 "1221") 0)))

  (testing "123425 = 4"
    (is (= (d1/ex2 "123425") 4)))

  (testing "123123 = 12"
    (is (= (d1/ex2 "123123") 12)))

  (testing "12131415"
    (is (= (d1/ex2 "12131415") 4))))
