from sklearn import ShuffleSplit, train_test_split
from sklearn.ensemble import GradientBoostingRegressor
from sklearn import GridSearchCV
import pandas as pd

# from DataProcessing import getTestBididColumn


def getTestBididColumn():
    usecols = ['bidid']
    dataset = pd.read_csv("/Users/shahrozahmed/Desktop/we_data/test.csv", usecols=usecols)
    dataset = dataset.iloc[:, 0].values
    return dataset


#Encode variables
def encode_var(dataset, variable):
    series = pd.Series(dataset[variable])
    encoded_domains = pd.get_dummies(series)
    dataset = dataset.drop(variable, axis=1)
    dataset = dataset.join(encoded_domains)
    return dataset

def optimise_domain(data, domain_keep_prob=0.02):
    unique_domain = data['domain'].value_counts()
    threshold = int(domain_keep_prob * len(unique_domain))
    domain_threshold = unique_domain[threshold]
    data['domain'].where(data['domain'].map(unique_domain) > domain_threshold, "unpopularFeatures", inplace=True)
    return encode_var(data, 'domain')

def getTrainTestData():
    # Importing the dataset

    usecols = ['click', 'city', 'region', 'slotwidth', 'slotheight', 'useragent', 'slotprice', 'usertag', 'weekday',
               'advertiser', 'slotvisibility', 'domain']

    usecolsTest = ['city', 'region', 'slotwidth', 'slotheight', 'useragent', 'slotprice', 'usertag', 'weekday',
               'advertiser', 'slotvisibility', 'domain']




    dataset = pd.read_csv('/Users/shahrozahmed/Desktop/we_data/train.csv', usecols=usecols) #train
    test = pd.read_csv("/Users/shahrozahmed/Desktop/we_data/test.csv", usecols=usecolsTest)

    dataset['os'] = 'os_'+dataset['useragent'].str.split('_').str[0]
    dataset['browser'] = 'browser_'+dataset['useragent'].str.split('_').str[1]
    dataset = dataset.drop('useragent', axis=1)

    test['os'] = 'os_'+test['useragent'].str.split('_').str[0]
    test['browser'] = 'browser_'+test['useragent'].str.split('_').str[1]
    test = test.drop('useragent', axis=1)

    #Encode creative
 #   s = pd.Series(dataset['creative'])
 #   encoded_domains = pd.get_dummies(s, 'creative')
  #  dataset = dataset.drop('creative', axis=1)
  #  dataset = dataset.join(encoded_domains)

 #   s2 = pd.Series(test['creative'])
 #   encoded_domains2 = pd.get_dummies(s2, 'creative')
 #   test = test.drop('creative', axis=1)
 #   test = test.join(encoded_domains2)

  #  print("first diff")
  #  diffCreative = list(set(encoded_domains) - set(encoded_domains2))
  #  print(diffCreative)
  #  dataset = dataset.drop(diffCreative, axis =1)

    print("second diff")
    diffCreative = list(set(dataset) - set(test))
    print(diffCreative)

    #Encode slotvisibility
    s = pd.Series(dataset['slotvisibility'])
    encoded_domains = pd.get_dummies(s, 'slotvisibility')
    dataset = dataset.drop('slotvisibility', axis=1)
    dataset = dataset.join(encoded_domains)
  #  dataset = dataset.drop('slotvisibility_Na', axis=1)

    s2 = pd.Series(test['slotvisibility'])
    encoded_domains2 = pd.get_dummies(s2, 'slotvisibility')
    test = test.drop('slotvisibility', axis=1)
    test = test.join(encoded_domains2)
 #   test = test.drop('slotvisibility_Na', axis=1)


    #Encode usertag
    newDatasetColumns = dataset['usertag'].str.get_dummies(sep=',')
    dataset = dataset.drop('usertag', axis =1)
    dataset = dataset.join(newDatasetColumns)

    newTestColumns = test['usertag'].str.get_dummies(sep=',')
    test = test.drop('usertag', axis=1)
    test = test.join(newTestColumns)

    # Encode Slotformat
 #   s = pd.Series(dataset['slotformat'])
 #   encoded_domains = pd.get_dummies(s, 'slotformat')
 #   dataset = dataset.drop('slotformat', axis=1)
 #   dataset = dataset.join(encoded_domains)
 #   dataset = dataset.drop('slotformat_Na', axis=1)

 #   s2 = pd.Series(test['slotformat'])
 #   encoded_domains2 = pd.get_dummies(s2, 'slotformat')
 #   test = test.drop('slotformat', axis=1)
 #   test = test.join(encoded_domains2)
 #   test = test.drop('slotformat_Na', axis=1)

    # Encode Browser
    browserSeries = pd.Series(dataset['browser'])
    browser_encoded_domains = pd.get_dummies(browserSeries)
    dataset = dataset.drop('browser', axis=1)
    dataset = dataset.join(browser_encoded_domains)

    browserSeries2 = pd.Series(test['browser'])
    browser_encoded_domains2 = pd.get_dummies(browserSeries2)
    test = test.drop('browser', axis=1)
    test = test.join(browser_encoded_domains2)

    # Encode OS
    osSeries = pd.Series(dataset['os'])
    os_encoded_domains = pd.get_dummies(osSeries)
    dataset = dataset.drop('os', axis=1)
    dataset = dataset.join(os_encoded_domains)

    osSeries2 = pd.Series(test['os'])
    os_encoded_domains2 = pd.get_dummies(osSeries2)
    test = test.drop('os', axis=1)
    test = test.join(os_encoded_domains2)


    dataset = optimise_domain(dataset)
    test = optimise_domain(test)

    val_features = list(test)
    dataset = dataset[val_features]

    diffCreative = list(set(dataset) - set(test))
    print("final diff")
    print(diffCreative)

    X_train = dataset.iloc[:, 1:len(dataset.columns)].values # colums 1, 2, 3,.. 5 are X
    y_train = dataset.iloc[:, 0].values #0 is index of what we want to predict...

    X_test = test.iloc[:, 0:len(test.columns)].values # colums 0, 2, 3,.. 5 are X
    y_test = test.iloc[:, 0].values #0 is index of what we want to predict...

    features = list(test)

    return X_train, y_train, X_test, y_test, features, dataset, test

def saveToFile(y_pred, x_test):

    csv = open('testing_bidding_price.csv', "w")
    columnTitleRow = "bidid,bidprice\n"
    csv.write(columnTitleRow)


    for i in range(0, len(x_test)):
        bidid = x_test[i]
        pred = y_pred[i]
        row = str(bidid) +","+str(pred)+"\n"
        csv.write(row)
    csv.close()


# Get the training and testing data
X_train, y_train, X_test, y_test, features, dataset, test = getTrainTestData()



#best_est = GradientBoostingRegressor(alpha=0.9, criterion='friedman_mse', init=None,
#             learning_rate=0.1, loss='ls', max_depth=5, max_features=1.0,
#             max_leaf_nodes=None, min_impurity_decrease=0.0,
#             min_impurity_split=None, min_samples_leaf=3,
#             min_samples_split=2, min_weight_fraction_leaf=0.0,
#             n_estimators=50, presort='auto', random_state=None,
#             subsample=1.0, verbose=0, warm_start=False)


best_est = GradientBoostingRegressor(alpha=0.9, criterion='friedman_mse', init=None,
             learning_rate=0.1, loss='ls', max_depth=10, max_features=1.0,
             max_leaf_nodes=None, min_impurity_decrease=0.0,
             min_impurity_split=None, min_samples_leaf=10,
             min_samples_split=2, min_weight_fraction_leaf=0.0,
             n_estimators=50, presort='auto', random_state=None,
             subsample=1.0, verbose=0, warm_start=False)

best_est.fit(X_train, y_train)

y_pred=best_est.predict(X_test)




#*****************

baseBid = 129.000000
avgCTR = 0.000738
bids = []
for i in range(len(y_pred)):
    clickProb = y_pred[i]
    bid = (baseBid * clickProb) / avgCTR
    bids.append(bid)


saveToFile(bids, getTestBididColumn())