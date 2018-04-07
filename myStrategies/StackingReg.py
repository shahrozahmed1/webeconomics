from mlxtend.regressor import StackingRegressor
from sklearn.linear_model import LinearRegression
from sklearn.ensemble import GradientBoostingRegressor
from evaluateStrategy import evaluate_strategy
from dataProcessing import *

from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVR
from mlxtend.regressor import StackingRegressor
from sklearn.svm import SVR
from sklearn.linear_model import Ridge
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error
from math import sqrt


usecols_lr = ['click', 'hour', 'weekday', 'region', 'advertiser', 'useragent', 'slotformat', 'slotvisibility',
           'adexchange', 'payprice'] #usertag
# Get the training and testing data

X_train, y_train, X_valid, y_valid, feat, dataset, validation = get_data(usecols_lr)

print("1")

ridgeReg = Ridge(alpha=0.05, normalize=True)
ridgeReg.fit(X_train, y_train)

# Predicting the Test set results
y_pred = ridgeReg.predict(X_valid)

print("2")

rms = sqrt(mean_squared_error(y_valid, y_pred))
print("linear regression rms= %f" % rms)
evaluate_strategy(y_pred, False)

print("########################")


gbrt = GradientBoostingRegressor(alpha=0.9, criterion='friedman_mse', init=None,
             learning_rate=0.1, loss='ls', max_depth=10, max_features=1.0,
             max_leaf_nodes=None, min_impurity_decrease=0.0,
             min_impurity_split=None, min_samples_leaf=10,
             min_samples_split=2, min_weight_fraction_leaf=0.0,
             n_estimators=50, presort='auto', random_state=None,
             subsample=1.0, verbose=0, warm_start=False)
gbrt.fit(X_train, y_train)

print("3")
# Predicting the Test set results
y_pred = gbrt.predict(X_valid)
rms = sqrt(mean_squared_error(y_valid, y_pred))

print("gbrt rms= %f" % rms)
evaluate_strategy(y_pred, False)
print("4")
regressors = [gbrt, ridgeReg]

lr = LinearRegression()

print("5")
stregr = StackingRegressor(regressors=regressors, meta_regressor=lr, refit=True)
stregr.fit(X_train, y_train)
print("6")
outpred = stregr.predict(X_valid)

print(outpred)
print("7")
evaluate_strategy(outpred)