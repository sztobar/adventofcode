(ns adventofcode2017.day4-test
  (:require [adventofcode2017.day4 :as d4]
            [clojure.test :refer :all]))

(deftest ex1-test

  (testing "\"aa bb cc dd ee\" is valid"
    (is (= (d4/is-valid? "aa bb cc dd ee") true)))

  (testing "\"aa bb cc dd aa\" is invalid"
    (is (= (d4/is-valid? "aa bb cc dd aa") false)))

  (testing "\"aa bb cc dd aaa\" is valid"
    (is (= (d4/is-valid? "aa bb cc dd aaa") true))))


(deftest ex2-test

  (testing "\"abcde fhhij\" doesn't have an anagram"
    (is (= (d4/has-anagram? "abcde fhhij") false)))

  (testing "\"abcde xyz ecdab\" has an anagram"
    (is (= (d4/has-anagram? "abcde xyz ecdab") true)))

  (testing "\"a ab abc abd abf abj\" doesn't have an anagram"
    (is (= (d4/has-anagram? "a ab abc abd abf abj") false)))

  (testing "\"iiii oiii ooii oooi oooo\" doesn't have an anagram"
    (is (= (d4/has-anagram? "iiii oiii ooii oooi oooo") false)))

  (testing "\"oiii ioii iioi iiio\" has an anagram"
    (is (= (d4/has-anagram? "oiii ioii iioi iiio") true))))
