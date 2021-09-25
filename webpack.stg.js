const path = require("path");
const webpack = require('webpack')
const {merge} = require('webpack-merge');
const common = require('./webpack.common.js');
const MODE = "production"

module.exports = merge(common, {
    mode: MODE,
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "src/main/resources/public"),
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env.NODE_ENV' : JSON.stringify('staging')
        })
    ]
});
