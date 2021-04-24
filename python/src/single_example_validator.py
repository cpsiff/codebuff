import numpy as np
import pylab
import matplotlib.pyplot as plt

style_1_err = [0.28214285,0.30837005,0.24045801,0.2600711,0.3425926,0.22848664,0.25561798,0.41111112,0.31099945,0.32448378]
style_2_err = [0.42738095,0.37004405,0.3683206,0.3613744,0.3611111,0.29970327,0.3258427,0.41666666,0.41931883,0.39528024]
style_google_err = [0.4,0.3524229,0.33969465,0.33471563,0.3611111,0.29376855,0.30617976,0.41111112,0.39810163,0.37758112]

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
