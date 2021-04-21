const path = require("path");
const webpack = require('webpack')
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    mode: "development",
    entry: "./src/main/resources/static/ts/index.tsx",
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "src/main/resources/public"),
    },
    devtool: "inline-source-map",
    devServer: {
        historyApiFallback: true,
        contentBase: path.join(__dirname, 'dist'),
        compress: true,
        port: 9000,
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./src/main/resources/static/ts/index.html",
            filename: "index.html",
        }),
        new webpack.ProvidePlugin({
            process: 'process/browser',
        }),
        new webpack.DefinePlugin({
            'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV ? process.env.NODE_ENV : "development")
        }),
    ],
    module: {
        rules: [
            {
                test: /\.css/,
                use: [
                    "style-loader",
                    {
                        loader: "css-loader",
                        options: {url: false},
                    },
                ],
            },
            {
                test: /\.tsx?$/,
                use: "ts-loader"
            },
            {
                test: /\.(jpg|png)$/,
                loader: 'url-loader'
            }
        ],
    },
    resolveLoader: {
        modules: [
            path.join(__dirname, 'node_modules')
        ]
    },
    resolve: {
        modules: [
            path.join(__dirname, 'node_modules')
        ],
        extensions: [".ts", ".tsx", ".js", ".json"]
    },
    target: ["web", "es5"],
};
