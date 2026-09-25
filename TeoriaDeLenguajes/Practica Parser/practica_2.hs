import Parsing

import Data.Char
import Control.Monad
import Control.Applicative hiding (many)

expr :: Parser Int
expr = do t <- term
          (do char '+'
              e <- expr
              return (t + e)
            <|> do char '-'
                   e <- expr
                   return (t - e)
            <|> return t)

term :: Parser Int
term = do f <- factor
          (do char '*'
              t <- term
              return (f * t)
            <|> do char '/'
                   t <- term
                   return (div f t)
            <|> return f)

factor :: Parser Int
factor = do d <- nat
            return d
          <|> do char '('
                 e <- expr
                 char ')'
                 return e

eval :: String -> Int
eval xs = fst (head (parse expr xs))



-- Ejercicio 2
-- linspace (start, end, n)        --> n = total_elem
-- [start, _ , _ , _ , ... , end]
-- delta = (end - start) / n - 1

--linspace :: (Fractional a2, Integral a1) => a2 -> a2 -> a1 -> [a2]
linspace _ _ 0 = []
linspace _ _ 1 = []
linspace start end n =
       let delta = (end - start) / fromIntegral (n - 1)
       in [start + delta * fromIntegral (j) | j <- [0..(n-1)]]