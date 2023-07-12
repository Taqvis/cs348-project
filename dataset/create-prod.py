import pandas as pd

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

# split artists into multiple rows
artistsDf = prodDf.explode("artists").rename(columns={"artists": "artist"})[["track_id", "artist"]]

# output sample dataset
prodDf.drop(columns="artists").to_csv("./dataset-tracks.csv", index=False)
artistsDf.to_csv("./dataset-artists.csv", index=False)
