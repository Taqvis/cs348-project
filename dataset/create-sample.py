import pandas as pd
import random

# set a seed for to reproduce our sample generation
# change the seed to obtain a different sample
random.seed("cs348")

# read the prod dataset
prodDf = pd.read_csv("./dataset-prod.csv", index_col=0)

# split artists into lists
prodDf = prodDf.assign(**{"artists": prodDf["artists"].str.split(';')})

# generate the indexes to select
sampleIndexes = random.sample(range(len(prodDf.index)), 100)

# get the rows at the indexes
sampleDf = prodDf.iloc[sampleIndexes]

# split artists into multiple rows
sampleArtists = sampleDf.explode("artists").rename(columns={"artists": "artist"})[["track_id", "artist"]]

# output sample dataset
sampleDf.drop(columns="artists").to_csv("./dataset-sample.csv")
sampleArtists.to_csv("./dataset-sample-artists.csv")
