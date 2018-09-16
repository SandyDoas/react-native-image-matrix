import React, { Component } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  NativeModules,
  Platform,
} from 'react-native';
const { RNImageMatrix } = NativeModules;

class ImageMatrix extends Component {
	constructor(props, context) {
		super(props, context);
		// this.passUrl().then(imagesMatrix => {
			// console.log(imagesMatrix)
			// this.props.getImagesMatrix = imagesMatrix;
			// {() => this.props.getImagesMatrix(imagesMatrix)}()
		// });
		if(Platform.OS === 'ios')
			this.passPathIos();
		else
			this.passPathAndroid();
	}

	passPathAndroid() {
        let rows        = this.props.rows;
        let columns     = this.props.columns;
		RNImageMatrix.getImageUrl(this.props.imagePath, this.props.type ,rows, columns,
			(images) => {
                console.log('Resultimages ',images);
                this.props.getImagesMatrix(images)
			});
	}
    async passPathIos() {
        let imagePath   = this.props.imagePath;
        let rows        = this.props.rows;
        let columns     = this.props.columns;
            try {
            const images = await RNImageMatrix.getImageUrl(this.props.imagePath,this.props.type,rows, columns);
            // const images = await RNImageMatrix.getName();
            console.log('images ', imagePath)
            this.props.getImagesMatrix(images)
            //return images;
        } catch(e) {
            console.error(e);
        }
    }
    // <View style={{flex: 1}}>
    //     <Text>Image is Downloaded Successfuly, Image Path : </Text>
    //     <Text>{this.props.imagePath}</Text>
    //     <TouchableOpacity onPress={() => this.handleAnd()}>
    //         <Text>{this.props.imagePath}</Text>
    //     </TouchableOpacity>
    // </View>
    render() {
        return (null);
    }
  }

export default ImageMatrix;
