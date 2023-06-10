import pandas as pd

# read the prod dataset
prodDf = pd.read_csv("./dataset.csv", index_col=0)

# split artists into lists
prodDf = prodDf.assign(**{"artists": prodDf["artists"].str.split(';')})

# split artists into multiple rows
artistsDf = prodDf.explode("artists").rename(columns={"artists": "artist"})[["track_id", "artist"]]

# output sample dataset
prodDf.drop(columns="artists").to_csv("./dataset-tracks.csv")
artistsDf.to_csv("./dataset-artists.csv")
