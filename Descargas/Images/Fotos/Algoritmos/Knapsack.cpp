#include <bits/stdc++.h>

using namespace std;

vector <int> weight;
vector <int> value;
vector <int> dim(2001, -1);
vector < vector <int> > res(2001, dim);

int mochila(int n, int s){
    if(res[n][s] != -1){
        return res[n][s];
    }
    
    if( n == value.size() ){
        res[n][s] = 0;
    }else if( weight[n] > s ){
        res[n][s] = mochila(n+1, s);
    }else{

        //Se agrega a la mochila
        int temp1 = value[n] + mochila(n + 1, s - weight[n]);
        //No se agrega a la mochila
        int temp2 = mochila(n + 1, s);
        res[n][s] = max(temp1, temp2);
    }

    return res[n][s];

}

int main(){

    int s, n, peso, valor;
    cin >> s >> n;

    for(int i = 0; i < n; i++){
        cin >> peso >> valor;
        weight.push_back(peso);
        value.push_back(valor);
    }

    cout << mochila(0, s) << endl;
    return 0;
}
