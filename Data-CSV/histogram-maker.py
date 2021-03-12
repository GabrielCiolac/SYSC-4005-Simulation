import matplotlib.pylot as plt
import pandas as pandas
import seaborn as sns

df_tips = pd.read_csv('https://raw.githubusercontent.com/GabrielCiolac/SYSC-4005-Simulation/development/Data-CSV/ws1.csv')

df_tips['time'].plot(kind='hist')