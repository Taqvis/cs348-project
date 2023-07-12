import pandas as pd
import random

# set a seed for to reproduce our sample generation
# change the seed to obtain a different sample
random.seed("cs348")

# read the prod dataset
prodDf = pd.read_csv("./dataset.csv", index_col=0)

# remove duplicates (cleanup)
prodDf = prodDf.drop_duplicates(subset=["track_id"])

# truncate track names that are too long
prodDf["track_name"] = prodDf["track_name"].str.slice(stop=254)

# split artists into lists
prodDf = prodDf.assign(**{"artists": prodDf["artists"].str.split(';')})

# update boolean explicit to 0/1 bit value
prodDf["explicit"] = prodDf["explicit"].astype(int)

# generate the indexes to select
sampleIndexes = random.sample(range(len(prodDf.index)), 100)

# get the rows at the indexes
sampleDf = prodDf.iloc[sampleIndexes]

# split artists into multiple rows
sampleArtists = sampleDf.explode("artists").rename(columns={"artists": "artist"})[["track_id", "artist"]]

# output sample dataset
sampleDf.drop(columns="artists").to_csv("./dataset-sample-tracks.csv", index=False)
sampleArtists.to_csv("./dataset-sample-artists.csv", index=False)
