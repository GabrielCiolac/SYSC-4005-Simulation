#!/usr/bin/env python
# coding: utf-8

# In[1]:


import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import math 
get_ipython().run_line_magic('matplotlib', 'inline')


# In[2]:

fName = input('Enter File Name:')

servinsp1 = pd.read_csv(fName)
servinsp1_array = np.asarray(servinsp1['time'])


# In[3]:


servinsp1_array


# In[16]:


bin_count = math.sqrt(servinsp1_array.size)
bin_count_min = math.ceil(bin_count)
bin_count_max =servinsp1_array.size/5
bin_count_max = math.ceil(bin_count_max)


# In[19]:


bin_count_max


# In[22]:


sns.displot(servinsp1_array,bins =  25,kde=True)


# In[94]:





# In[ ]:




