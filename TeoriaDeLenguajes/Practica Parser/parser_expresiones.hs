import Parsing
import Control.Monad
import Control.Applicative ((<|>))

{--
expr     -> expr ('+' term | '-' term) | term
term     -> term ('*' factor | '/' factor) | factor
factor   -> digit | '(' expr ')'
digit    -> '0' | '1' | '2' | ... | '9'

A -> A a | b -- a no vacio y b no comenzar con A
=>
A  -> b A'
A' -> e | a A'
  A           A            a               b
__|_        __|_ __________|__________   __|_
expr     -> expr ('+' term | '-' term) | term

expr  -> term expr'
expr' -> e | ('+' term | '-' term) expr'


A           A                a                 b
__|_        __|_  ___________|__________     __|__
term     -> term ('*' factor | '/' factor) | factor

term  -> factor term'
term' -> e | ('*' factor | '/' factor) term'

{--
"5 - 3 - 2" (5-3)-2

expr -> term expr'
     -> factor term' '-' term expr'
     -> factor term' '-' term '-' term expr'
     -> digit e '-' factor term' '-' factor term' e
     -> 5 '-' digit e '-' digit e
     -> 5 '-' 3 '-' 2
--}


-- sin recursión izquierda
expr  -> term expr'
expr' -> '+' term expr'| '-' term expr' | e
term  -> factor term'
term' -> e | ('*' factor | '/' factor) term'
factor   -> digit | '(' expr ')'
digit    -> '0' | '1' | '2' | ... | '9'

--}
expr :: Parser Int
expr = do x <- term
          f <- expr'
          return (f x)

expr' :: Parser (Int -> Int)
expr' = do token (char '+')
           t <- term
           f <- expr'
           return (f.(\x->x+t))
         <|>
           do token (char '-')
              t <- term
              f <- expr'
              return (f.(\x->x-t))
         <|> return id

term :: Parser Int
term = do x <- factor
          f <- term'
          return (f x)

term' :: Parser (Int -> Int)
term' = do token (char '*')
           t <- factor
           f <- term'
           return (f.(\x->x*t))
        <|>
           do token (char '/')
              t <- factor
              f <- term'
              return (f.(\x->x `div` t))
        <|> return id

factor :: Parser Int
factor = do d <- nat
            return d
           <|> do char '('
                  e <- expr
                  char ')'
                  return e

eval :: String -> Int
eval xs = fst (head (parse expr xs))