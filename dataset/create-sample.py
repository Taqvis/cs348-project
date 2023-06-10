import pandas as pd
import random

# set a seed for to reproduce our sample generation
# change the seed to obtain a different sample
random.seed("cs348")

# read the prod dataset
prodDf = pd.read_csv("./dataset-prod.csv", index_col=0)

# generate the indexes to select
sampleIndexes = random.sample(range(len(prodDf.index)), 100)

# get the rows at the indexes
sampleDf = prodDf.iloc[sampleIndexes]

# output sample dataset
sampleDf.to_csv("./dataset-sample.csv")
