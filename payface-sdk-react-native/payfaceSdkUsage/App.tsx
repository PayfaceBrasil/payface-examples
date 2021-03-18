/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { Component } from 'react';
import { 
    View,
    StyleSheet,
    requireNativeComponent,
    findNodeHandle,
    Button,  
    Alert,
    UIManager,
} 
from 'react-native';

const PayfaceSDK = requireNativeComponent('RCTPayfaceSDKFragment');

export default class RCTPayfaceSDKFragment extends Component {
    nativeComponetRef : any;
    commandName  = 1;

    componentDidMount() {
        setTimeout(() => {
            this.create();
        }, 1000)
    }

    create = () => {
        const androidViewId = findNodeHandle(this.nativeComponetRef);
        console.log('id fragmente', androidViewId);
        console.log('Send command');
        UIManager.dispatchViewManagerCommand(
          androidViewId,
          UIManager.RCTPayfaceSDKFragment.Commands.create.toString(),
          [androidViewId]
        )
    }

    render() {
        return (
        <View style={styles.container}>
           <Button 
              title="Payface SDK" 
              onPress={() => Alert.alert('Aplicativo de demonstração \nReact Native + SDK Payface')}
              />
            <PayfaceSDK
                style={styles.container}
                partner="angeloni"
                nameClient="Jonathas Pinheiro Trindade"
                cpf="12354565434"
                cellphone="98123458741"
                environment="production"
                ref={(nativeRef) => (this.nativeComponetRef = nativeRef)}
            />
        </View>
        )
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 24,
    backgroundColor: "#eaeaea"
  },
});
