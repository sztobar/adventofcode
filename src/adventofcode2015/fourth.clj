(ns adventofcode2015.fourth
  (:import java.security.MessageDigest
           java.math.BigInteger))

(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
   (str padding sig)))

(def regex #"(0{5})(\d+).*")

(defn get-advent-coin
  ([s] (get-advent-coin s regex))
  ([s regex]
   (loop [n 0]
    (let [h (md5 (str s n))
          matches (re-matches regex h)]
      (if (nil? matches)
        (recur (+ n 1))
        n)))))

(def regex2 #"(0{6})(\d+).*")
