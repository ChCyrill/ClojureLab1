(ns lab1.core
  (:gen-class)
  (require [clojure.java.io :as io]
           [clojure.string :as str]
           [clojure.math.numeric-tower :as math]
           )
  )

(defn distance
  [firstPoint secondPoint]
  (math/sqrt (reduce + (map #(math/expt (- %1 %2) 2) firstPoint secondPoint)))
 )

(defn readLine
  [line]
  (map #(Float/parseFloat %) (re-seq #"[\d\.]+" line))
  )

(defn readFile
  [fileName]
  (with-open [inFile (io/reader fileName)]
      (map #(readLine %) (doall (line-seq inFile)))
    )
  )

(defn calcPotential
  [point data alpha]
  (reduce +
    (map
      #(Math/exp (- (* alpha (math/expt % 2))))
      (map
        #(distance point %)
        data
        )
      )
    )
  )

(defn calcPotentials
  [data alpha]
  (doall 
    (map
      #(calcPotential %1 data alpha) 
      ;;#(println %1 data alpha)
      data
      )
    )
  )

(defn recalcPotential
  [point potential data beta core corePotential]
  (- 
    potential 
    (* corePotential 
       (Math/exp 
         (- 
           (* beta 
              (math/expt (distance point core) 2))))))
  )

(defn recalcPotentials
  [potentials data beta core corePotential]
  (map
    #(recalcPotential %1 %2 data beta core corePotential)
    data potentials))

(defn maxPotentialPoint
  [potentials]
  (apply max-key #(second %) (map-indexed vector potentials))
  )

(defn minDistanceToCores
  [data potentialCore cores]
  (let 
    [distances (map #(vector (first %) (distance
                                         (nth data (first potentialCore))
                                         (nth data (first %)))) cores)] 
    (apply min-key #(second %) distances)
    ))

(defn -main
  [fileName ra]
  (let [ra (Float/parseFloat ra)]
  (let [alpha (/ 4.0 (math/expt ra 2))] 
  (let [rb (* 1.5 ra) beta (/ 4.0 (math/expt rb 2))]
  (let [data (readFile fileName)]
    (let [potentials (calcPotentials data alpha)]
      (let [firstCore (maxPotentialPoint potentials)]
        (loop [potentials (recalcPotentials 
                        potentials
                        data 
                        beta 
                        (nth data (first firstCore)) 
                        (second firstCore)
                        ) cores [firstCore]]
          (let [core (maxPotentialPoint potentials)]
            (if (> (/ (second core) (second firstCore)) 0.5)
                (recur (recalcPotentials 
                        potentials
                        data 
                        beta 
                        (nth data (first core)) 
                        (second core)
                        ) (conj cores core))
              ;else
              (if (> (/ (second core) (second firstCore)) 0.15)
                (let [minDistance (minDistanceToCores data core cores)]
                  (if (>= 1 
                          (+ (/ (second minDistance) ra) 
                             (/ (nth potentials (first minDistance)) (second firstCore))))
                             ;(nth potentials (first minDistance))))
                    (recur (recalcPotentials 
                                         potentials
                                         data 
                                         beta 
                                         (nth data (first core)) 
                                         (second core)) 
                           (conj cores core))
                    ;else
                    (let [potetials (assoc (apply vector potentials) (first core) 0.0)]
                      (recur (recalcPotentials 
                                           potentials
                                           data 
                                           beta 
                                           (nth data (first core)) 
                                           (second core))
                             (conj cores core)))
                      )
                    )
                  ;else
                  (doall(map #(println (first %)) cores)))
                )
              )
          )
      )
    )
    )
    )
    )
    )
  )
  

