#Web Data Analysis
#DataSet: MSNBC Anonymous Web Data
#authors: Dileep Kumar Thallapally
#University of Akron

##########################################################
#Usage:
#baskets<-load()
#sequences<-fit(baskets)
library(arules)
library(arulesSequences)
#################################
#Load Data
load <-function(file="~/DataSet/Preprocessed/transformed.seq") 
{
  b = read_baskets("~/DataSet/Preprocessed/transformed.seq", info = c("sequenceID","eventID","SIZE"))
  return (b)
}
#Mine the set of frequent sequences.
freqsequences <- function(input, support=0.01)
{
  s1 <- cspade(baskets, parameter = list(support = 0.01), control = list(verbose = TRUE))
  return (s1)
}
#Generate Association rules
AssociationRules <- function(freqsequences, confidence=0.01)
{
r7 <- ruleInduction(freqsequences, confidence = 0.5,control    = list(verbose = TRUE))
}