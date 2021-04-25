import numpy as np
import pylab
import matplotlib.pyplot as plt

style_1_err = [0.11811024,0.17012449,0.16977613,0.1100397,0.17073171,0.16066483,0.13315926,0.2513089,0.16816026,0.15789473]
style_2_err = [0.15224534,0.21632653,0.1923775,0.14964108,0.2682927,0.25274727,0.17054264,0.24747474,0.201849,0.15135135]
style_google_err = [0.1327334,0.21161826,0.21641791,0.14577425,0.17886178,0.25484765,0.15926893,0.2460733,0.19082762,0.14127424]

language_data = [style_1_err, style_2_err, style_google_err]
labels = ["Style 1", "Style 2", "Google"]
fig = plt.figure()
ax = plt.subplot(111)
ax.boxplot(language_data,
           whis=[10, 90], # 10 and 90 % whiskers
           widths=.35,
           labels=labels,
           showfliers=False)
ax.set_xticklabels(labels, rotation=60, fontsize=10)
ax.tick_params(axis='both', which='major', labelsize=10)
plt.xticks(range(1,len(labels)+1), labels, rotation=60, fontsize=10)
# pylab.ylim([0,.15])
ax.yaxis.grid(True, linestyle='-', which='major', color='lightgrey', alpha=0.5)
ax.set_xlabel("Grammar", fontsize=12)
ax.set_ylabel("Misclassification Error Rate", fontsize=12)
ax.set_title("Error Rate When Training on a Single Hand-Made Example File")
plt.tight_layout()
fig.savefig('images/leave_one_out.pdf', format='pdf')
plt.show()
