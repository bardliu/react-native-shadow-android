import * as React from 'react';

import {StyleSheet} from 'react-native';
import View from 'react-native-shadow-android';

export default function App() {
  return (
    <View style={styles.container}>
      <View style={[styles.box, styles.shadow1]} />
      <View style={[styles.box, styles.shadow2]} />
      <View style={[styles.box, styles.shadow3]} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    margin: 10,
    width: 100,
    height: 100,
    backgroundColor: '#fff',
  },
  shadow1: {
    shadowColor: 'rgba(142, 142, 142, 0.5)',
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowRadius: 8,
    shadowOpacity: 1,
  },
  shadow2: {
    shadowColor: 'rgba(142, 142, 142, 0.5)',
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowRadius: 8,
    shadowOpacity: 1,
    borderRadius: 40,
    borderBottomEndRadius: 80,
  },
  shadow3: {
    shadowColor: 'red',
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowRadius: 8,
    shadowOpacity: 1,
    borderRadius: 40,
  },
});
