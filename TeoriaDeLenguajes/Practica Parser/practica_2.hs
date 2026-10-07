import Parsing

import Data.Char
import Control.Monad
import Control.Applicative hiding (many)


--------------------
-- Ejercicio 1
-- parse (trans nat) "1"
--------------------
trans :: Parser a -> Parser a
trans p = 
    do
        char '('
        e <- p
        char ')'
        return e
    <|>
    do
        e <- p
        return e


--------------------
-- Ejercicio 2
-- linspace (start, end, n)        --> n = total_elem
-- [start, _ , _ , _ , ... , end]
-- delta = (end - start) / n - 1
--------------------

--linspace :: (Fractional a2, Integral a1) => a2 -> a2 -> a1 -> [a2]
linspace _ _ 0 = []
linspace _ _ 1 = []
linspace start end n =
       let delta = (end - start) / fromIntegral (n - 1)
       in [start + delta * fromIntegral (j) | j <- [0..(n-1)]]

--------------------
-- Ejercicio 3
-- parse listh "[1,'a',25,'3']"
--------------------
data Element = Integer Int | Character Char deriving Show

element :: Parser Element
element =
    do
        d <- int
        return (Integer d)
    <|>
    do
        char '\''
        c <- alphanum
        char '\''
        return (Character c)
    
elements :: Parser [Element]
elements =
    do
        x <- element
        xs <- moreElements
        return (x : xs)
    <|>
    return []

moreElements :: Parser [Element]
moreElements =
    do
        char ','
        elements
    <|>
    return []

listh :: Parser [Element]
listh = 
    do
        char '['
        xs <- elements
        char ']'
        return xs


--------------------
-- Ejercicio 4
-- expr → term (’+’ expr | ’-’ expr | emptyS)
-- term → factor (’*’ term | ’/’ term | emptyS)
--------------------

