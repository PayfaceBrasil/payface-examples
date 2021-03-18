# Integração com o SDK Payface

1. Leia o [README.md](https://github.com/PayfaceBrasil/payface-app-integration-sdk-android) do projeto do SDK versão Android para realizar a configuração do SDK da Payface no seu projeto android.

2. Copie as classe abaixo e cole dentro pacote de seu projeto.:

```
payface-sdk-react-native/payfaceSdkUsage/android/app/src/main/java/com/payfacesdkusage/PayfaceSDKModuleManager.java
payface-sdk-react-native/payfaceSdkUsage/android/app/src/main/java/com/payfacesdkusage/PayfaceSDKPackage.java
```

3. Modifique o package dessas classe pro padrão do seu projeto

4. No MainApplication do seu projeto, adicione o pacote da payface criado.

```
@Override
protected List<ReactPackage> getPackages() {
 List<ReactPackage> packages = new PackageList(this).getPackages();
 packages.add(new PayfaceSDKPackage());
 return packages;
}
```

5. Não esqueça de copiar o payface-app.aar mais recente para dentro de app/libs no seu projeto

6. Não esquece de configurar o app/build.gradle com as dependências necessárias para utilização da SDK da Payface.
