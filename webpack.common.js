const path = require("path");
const webpack = require('webpack')
const HtmlWebpackPlugin = require("html-webpack-plugin");
const MODE = "development";
const enabledSourceMap = MODE === "development";

module.exports = {
    mode: MODE,
    entry: "./src/main/typescript/index.tsx",
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "public"),
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./src/main/typescript/index.html",
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
                test: /\.scss/,
                use: [
                    "style-loader",
                    {
                        loader: "css-loader",
                        options: {
                            sourceMap: enabledSourceMap
                        },
                    },
                    {
                        loader: "postcss-loader",
                        options: {
                            postcssOptions: {
                                plugins: [
                                    ["autoprefixer", {grid: true}],
                                ],
                            },
                        },
                    },
                    {
                        loader: "resolve-url-loader"
                    },
                    {
                        loader: "sass-loader",
                        options: {
                            sourceMap: enabledSourceMap
                        }
                    }
                ],
            },
            {
                test: /\.(gif|png|jpg|eot|wof|woff|ttf|svg)$/,
                type: "asset/inline",
            },
            {
                test: /\.tsx?$/,
                use: "ts-loader"
            },
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
