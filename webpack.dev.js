const path = require("path");
const {merge} = require('webpack-merge');
const common = require('./webpack.common.js');
const MODE = "development";

module.exports = merge(common, {
    mode: MODE,
    devtool: "inline-source-map",
    devServer: {
        historyApiFallback: true,
        contentBase: path.join(__dirname, 'dist'),
        compress: true,
        port: 9000,
    },
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "src/main/resources/public"),
    },
});
