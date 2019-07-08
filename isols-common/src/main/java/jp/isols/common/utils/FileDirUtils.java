package jp.isols.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * ファイルユーティリティクラス
 */
public class FileDirUtils {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(FileDirUtils.class);

    private static final int BUFFER_SIZE = 4096;

    private FileDirUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * ファイル、または、ディレクトリが存在するかを判断する。
     *
     * @param target 対象
     * @return 判断結果
     * @retval true 存在するファイル、または、ディレクトリ
     * @retval false 存在しないファイル、または、ディレクトリ
     */
    public static boolean isExists(final Path target) {
        if (target == null) {
            return false;
        }

        if (StringUtils.isEmpty(target.toString())) {
            return false;
        }

        return Files.exists(target);
    }

    /**
     * ファイルが存在するかを判断する。

     * @param fileDir 対象ディレクトリ
     * @param fileName 対象ファイル名
     * @return 判断結果
     * @retval true 存在するファイル
     * @retval false 存在しないファイル
     */
    public static boolean isExists(final String fileDir, final String fileName) {
        if (StringUtils.isBlank(fileDir) || StringUtils.isBlank(fileName)) {
            return false;
        }

        Path filePath = Paths.get(fileDir, fileName);
        return Files.exists(filePath);
    }

    /**
     * ファイル・ディレクトリをコピーする。
     *
     * @param target コピー元
     * @param path コピー先
     * @note コピー元がファイルで、コピー先が存在するファイルの場合、上書きを行う。<br>
     *       コピー先がディレクトリの場合、その直下にコピーを行う。
     */
    public static void copy(final Path target, final String path) throws IOException {
        if (isExists(target) == false) {
            return;
        }

        if (Files.isRegularFile(target)) {
            copyPath(target, path);
        } else if (Files.isDirectory(target)) {
            copyPath(target, path);
            final Path toPath = Paths.get(path, target.getFileName().toString());
            Files.walkFileTree(target, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
                    Files.createDirectories(toPath.resolve(target .relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    try {
                        Files.copy(file, toPath.resolve(target.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                    } catch (NoSuchFileException e) {
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    /**
     * ファイル・ディレクトリをコピーする。
     *
     * @param target コピー元
     * @param path コピー先
     * @note copyメソッドにより呼び出される。
     */
    private static void copyPath(final Path target, final String path) throws IOException {
        if (Files.isDirectory(Paths.get(path))) {
            Files.copy(target, Paths.get(path, target.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.copy(target, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * ファイル・ディレクトリを移動する。
     *
     * @param target 移動元
     * @param path 移動先
     * @note 移動元がファイルで、移動先が存在するファイルの場合、上書きを行う。<br>
     *       移動先がディレクトリの場合、その直下に移動を行う。
     */
    public static void move(final Path target, final String path) throws IOException {
        if (isExists(target) == false) {
            return;
        }

        if (Files.isRegularFile(target)) {
            movePath(target, path);
        } else if (Files.isDirectory(target)) {
            copyPath(target, path);
            final Path toPath = Paths.get(path, target.getFileName().toString());
            Files.walkFileTree(target, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
                    Files.move(dir, toPath.resolve(target.relativize(dir)), StandardCopyOption.ATOMIC_MOVE);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path file, final IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    try {
                        Files.move(file, toPath.resolve(target.relativize(file)), StandardCopyOption.ATOMIC_MOVE);
                    } catch (NoSuchFileException e) {
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    /**
     * ファイル・ディレクトリを移動する。
     *
     * @param target 移動元
     * @param path 移動先
     * @note moveメソッドにより呼び出される。
     */
    private static void movePath(final Path target, final String path) throws IOException {
        if (Files.isDirectory(Paths.get(path))) {
            Files.move(target, Paths.get(path, target.getFileName().toString()), StandardCopyOption.ATOMIC_MOVE);
        } else {
            Files.move(target, Paths.get(path), StandardCopyOption.ATOMIC_MOVE);
        }
    }

    /**
     * ファイル・ディレクトリを削除する。
     *
     * @param target 対象
     */
    public static void delete(final Path target) throws IOException {
        if (isExists(target) == false) {
            return;
        }

        if (Files.isRegularFile(target)) {
            Files.delete(target);
        } else if (Files.isDirectory(target)) {
            Files.walkFileTree(target, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    /**
     * ファイル・ディレクトリをリネームする。
     *
     * @param target リネーム元
     * @param newName リネーム後の名前
     */
    public static void rename(final Path target, final String newName) throws IOException {
        Files.move(target, Paths.get(target.getParent().toString(), newName), StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * ファイルを作成する。
     *
     * @param target ファイルのパス
     */
    public static String createFile(final String target) throws IOException {
        Files.createFile(Paths.get(target));
        return Paths.get(target).toAbsolutePath().toString();
    }

    /**
     * ファイルを作成する。
     *
     * @param dir ファイルを格納するディレクトリ
     * @param extension ファイルの拡張子
     * @note ファイルはUUIDから作成する。
     */
    public static String createFile(final String fileDir, final String extension) throws IOException {
		final String fileName = UUID.randomUUID().toString();
        return createFile(fileDir + File.separator + fileName + (StringUtils.isEmpty(extension) ? "" : "." + extension));
    }

    /**
     * ディレクトリを作成する。
     *
     * @param target ディレクトリのパス
     */
    public static void createDirectory(final String target) throws IOException {
        Files.createDirectories(Paths.get(target));
    }

    /**
     * ドライブレターを取得する。
     *
     * @return ドライブレター
     */
    public static String getDriveLetter() {
        return getDriveLetter(Paths.get("").toAbsolutePath().toString());
    }

    /**
     * ドライブレターを取得する。
     *
     * @param target 対象
     * @return ドライブレター
     */
    public static String getDriveLetter(final String target) {
        Pattern pattern = Pattern.compile("^([a-zA-Z]):[\\\\/].*");
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return matcher.replaceFirst("$1:");
        }

        return "";
    }

    /**
     * ファイルリストを取得する。
     *
     * @param target 対象
     * @return サブディレクトリを含めた全ファイルリスト
     */
    public static List<Path> getFileList(final Path target) throws IOException {
        if (target == null || !isExists(target) || !Files.isDirectory(target)) {
            return new ArrayList<>();
        }

        return Files.list(target).collect(Collectors.toList());
    }

    /**
     * 拡張子を取得する。
     *
     * @param target 対象
     * @return ドット(.)を含めない拡張子
     */
    public static String getFileExtension(final String target) {
        if (StringUtils.isBlank(target)) {
            return "";
        }

        String fileName = new File(target).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * 文字コードを取得する。
     *
     * @param bytes 対象バイト配列
     * @return 文字コード
     */
	public static final String guessEncoding(final byte[] bytes) {
		final UniversalDetector detector = new UniversalDetector(null);
		detector.handleData(bytes, 0, bytes.length);
		detector.dataEnd();
		final String encoding = detector.getDetectedCharset();
		detector.reset();

		return encoding == null ? StringUtils.UTF_8.name() : encoding;
	}

    /**
     * 文字コードをCharsetとして取得する。
     *
     * @param bytes 対象バイト配列
     * @return Charsetクラスのインスタンス
     */
	public static final Charset getCharset(final byte[] bytes) {
		return Charset.forName(guessEncoding(bytes));
	}

    /**
     * 文字コードをCharsetとして取得する。
     *
     * @param filePath 対象
     * @return Charsetクラスのインスタンス
     */
	public static final Charset getCharset(final Path filePath) {
		try {
			return getCharset(Files.readAllBytes(filePath));
		} catch (IOException e) {
            return StringUtils.UTF_8;
		}
	}

    /**
     * ファイルを読み込む。
     *
     * @param filePath 対象
     * @param charset 文字コード
     * @return 読み込んだファイルの行ごとのリスト
     */
    public static List<String> readFile(final Path filePath, final Charset charset) {
        if (!isExists(filePath)) {
            return new ArrayList<>();
        }

        if (!Files.isReadable(filePath)) {
            return new ArrayList<>();
        }

        try {
			return Files.readAllLines(filePath, charset);
		} catch (IOException e) {
            return new ArrayList<>();
		}
    }

    /**
     * ファイルを読み込む。
     *
     * @param filePath 対象
     * @return 読み込んだファイルの行ごとのリスト
     * @note 文字コードは自動判別を行う。
     */
    public static List<String> readFile(final Path filePath) {
        return readFile(filePath, getCharset(filePath));
    }

    /**
     * Base64に変換する。
     *
     * @param filePath 対象
     * @return Base64に変換後のバイト配列
     * @throws IOException
     */
    public static byte[] base64(final Path filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(filePath);
        return Base64.encodeBase64(bytes);
    }

    /**
     * ファイルの行数を取得する。
     *
     * @param filePath 対象
     * @param charset 文字コード
     * @return 読み込んだファイルの行数
     * @throws IOException
     */
    public static long getLineNumber(final Path filePath, final Charset charset) throws IOException {
        return Files.lines(filePath, charset).count();
    }

    /**
     * ファイルの行数を取得する。
     *
     * @param filePath 対象
     * @return 読み込んだファイルの行数
     * @throws IOException
     * @note 文字コードは自動判別を行う。
     */
    public static long getLineNumber(final Path filePath) throws IOException {
        return Files.lines(filePath, getCharset(filePath)).count();
    }

    /**
     * ファイルに書き込む。
     *
     * @param filePath 対象
     * @param lineList 行ごとの文字列リスト
     * @param charset 文字コード
     * @throws IOException
     */
    public static void writeFile(final Path filePath, final List<String> lineList, final Charset charset) throws IOException {
        if (lineList.isEmpty()) {
            return;
        }

        if (!isExists(filePath)) {
            Files.createFile(filePath);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) {
            lineList.stream().forEach(x -> {
                    try {
                        writer.write(x, 0, x.length());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ファイルに書き込む。
     *
     * @param filePath 対象
     * @param lineList 行ごとの文字列リスト
     * @throws IOException
     * @note 文字コードは'UTF-8'とする。
     */
    public static void writeFile(final Path filePath, final List<String> lineList) throws IOException {
        writeFile(filePath, lineList, StringUtils.UTF_8);
    }

    /**
     * CSVファイルを読み込む。
     *
     * @param filePath 対象
     * @return ファイル行ごと、行単位のCSVカラムごとのリスト
     */
    public static List<List<String>> readCsvFile(final Path filePath) {
        if (!isExists(filePath)) {
            return new ArrayList<>();
        }

        if (!Files.isReadable(filePath)) {
            return new ArrayList<>();
        }

        try {
            final CSVFormat csvFormat = CSVFormat.EXCEL
                        .withAllowMissingColumnNames(true)
                        .withIgnoreEmptyLines(true)
                        .withIgnoreSurroundingSpaces(true);
            CSVParser parser = CSVParser.parse(filePath, FileDirUtils.getCharset(Files.readAllBytes(filePath)), csvFormat);

            List<List<String>> resultList = new LinkedList<List<String>>();
            List<CSVRecord> recordList = parser.getRecords();
            recordList.stream().forEach(x -> {
            		List<String> recordListByLine = new LinkedList<String>();
            		for (int i = 0; i < x.size(); i++) {
            			recordListByLine.add(x.get(i));
            		}
            		resultList.add(recordListByLine);
                });

            return resultList;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * CSVファイルに書き込む。
     *
     * @param filePath 対象
     * @param csvLineList ファイル行ごと、行単位のCSVカラムごとのリスト
     * @param charset 文字コード
     * @throws IOException
     * @note すでに存在するファイルを指定した場合は、追記する。
     */
    public static void writeCsvFile(final Path filePath, final List<List<String>> csvLineList, final Charset charset) throws IOException {
        if (csvLineList.isEmpty()) {
            return;
        }

        if (!isExists(filePath)) {
            Files.createFile(filePath);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) {
            CSVPrinter printer = CSVFormat.EXCEL.print(writer);
            csvLineList.stream().forEach(x -> {
                    try {
                        printer.printRecord(x);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * CSVファイルに書き込む。
     *
     * @param filePath 対象
     * @param csvLineList ファイル行ごと、行単位のCSVカラムごとのリスト
     * @throws IOException
     * @note 文字コードは'UTF-8'とする。
     */
    public static void writeCsvFile(final Path filePath, final List<List<String>> csvLineList) throws IOException {
        writeCsvFile(filePath, csvLineList, StringUtils.UTF_8);
    }

    /**
     * ZIPに圧縮する。
     *
     * @param paths 対象
     * @param dirPath 圧縮ファイルの格納ディレクトリ
     * @param fileName 圧縮後ファイル名
     * @throws IOException
     * @note 圧縮ファイルのファイル名エンコードは'UTF-8'とする。
     */
    public static void compressToZip(final Path[] paths, final String dirPath, final String fileName) throws IOException {

        final Path directory = Paths.get(dirPath);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        ZipOutputStream zipOutputStream = null;
        try {
            Path zipFile = Paths.get(directory.toAbsolutePath().toString(), fileName);
            zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
            zipOutputStream.setEncoding(StringUtils.UTF_8.displayName());

            compress(zipOutputStream, paths);
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
        }
    }

    /**
     * ファイルを圧縮する。
     *
     * @param zipOutputStream ZipOutputStreamクラスのインスタンス
     * @param paths 対象
     * @throws IOException
     * @note compressToZipメソッドにより呼び出される。
     */
    private static void compress(final ZipOutputStream zipOutputStream, final Path[] paths) throws IOException {
        InputStream inputStream = null;
        CRC32 crc = new CRC32();
        byte[] buf = new byte[1024];

        for (final Path path : paths) {
            if (Files.isDirectory(path)) {
                compress(zipOutputStream, (Path[]) Files.list(path).toArray());
                continue;
            }

            try {
                inputStream = Files.newInputStream(path);

                int length = 0;
                while ((length = inputStream.read(buf)) != -1) {
                    crc.update(buf, 0, length);
                }
                inputStream.close();

                ZipEntry entry = new ZipEntry(
                    Normalizer.normalize(path.getFileName().toString(), Normalizer.Form.NFC));
                entry.setMethod(ZipEntry.DEFLATED);
                entry.setSize(Files.size(path));
                entry.setCrc(crc.getValue());
                zipOutputStream.putNextEntry(entry);

                inputStream = Files.newInputStream(path);
                while ((length = inputStream.read(buf, 0, buf.length)) != -1) {
                    zipOutputStream.write(buf, 0, length);
                }

                zipOutputStream.closeEntry();
                zipOutputStream.flush();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
    }

    /**
     * ZIPを解凍する。
     *
     * @param path 対象
     * @param dirPath 解凍先のディレクトリ
     * @param charset 圧縮ファイルのファイル名エンコード
     * @throws IOException
     */
    public static void uncompressToZip(final Path path, final String dirPath, final Charset charset) throws IOException {

        final Path directory = Paths.get(dirPath);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(path.toAbsolutePath().toString(), charset.displayName());

            Enumeration<? extends ZipEntry> enumeration = zipFile.getEntries();
            while (enumeration.hasMoreElements()) {
                final ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();

                if (zipEntry.isDirectory()) {
                    Files.createDirectories(Paths.get(dirPath, zipEntry.getName()));
                } else {
                    final Path outPath = Paths.get(dirPath, zipEntry.getName());
                    if (!Files.exists(outPath.getParent())) {
                        Files.createDirectories(outPath.getParent());
                    }

                    byte[] buffer = new byte[1024];
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    OutputStream outputStream = Files.newOutputStream(outPath);
                    int readSize = 0;
                    while ((readSize = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, readSize);
                    }

                    outputStream.close();
                    inputStream.close();
                }
            }
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
        }
	}

    /**
     * ZIPを解凍する。
     *
     * @param path 対象
     * @param dirPath 解凍先のディレクトリ
     * @throws IOException
     * @note 圧縮ファイルのファイル名エンコードは'UTF-8'とする。
     */
    public static void uncompressToZip(final Path path, final String dirPath) throws IOException {
        uncompressToZip(path, dirPath, StringUtils.UTF_8);
    }

    /**
     * 圧縮ファイルのファイル名エンコードを取得する。
     *
     * @param zipFilePath 対象
     * @return 圧縮ファイルのファイル名エンコード
     * @throws IOException
     */
	public static String getZipEncode(final Path zipFilePath) throws IOException {
        final Path rootPath = Paths.get(".");
        ZipFile zipFile = null;
        boolean asciiOnly = true;
        try {
            zipFile = new ZipFile(zipFilePath.toAbsolutePath().toString(), StringUtils.MS932.displayName());
            Enumeration<? extends ZipEntry> enumeration = zipFile.getEntries();
            while (enumeration.hasMoreElements()) {
                final ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();
                final String str = Paths.get(rootPath.toString(), zipEntry.getName()).toAbsolutePath().toString();
                if (!StringUtils.isAscii(str)) {
                    asciiOnly = false;
                }
            }
        } catch (MalformedInputException e) {
            return StringUtils.UTF_8.displayName();
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
        }

        return asciiOnly ? StringUtils.UTF_8.displayName() : StringUtils.MS932.displayName();
    }

    /**
     * ZIPファイル内のエントリー一覧を取得する。
     *
     * @param zipFilePath 対象
     * @param charset 圧縮ファイルのファイル名エンコード
     * @return 圧縮ファイル内のファイルリスト
     * @throws IOException
     */
	public static List<Path> getZipEntries(final Path zipFilePath, final Charset charset) throws IOException {
		final List<Path> resultList = new ArrayList<>();
        Map<String, String> env = new HashMap<>();
        env.put("create", "false");
        env.put("encoding", charset.displayName());

		try (FileSystem fileSystem = FileSystems.newFileSystem(createZipUri(zipFilePath), env, ClassLoader.getSystemClassLoader())) {
			for (final Path rootPath : fileSystem.getRootDirectories()) {
				Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						resultList.add(file);
						return FileVisitResult.CONTINUE;
					}
				});
			}
		}

		return resultList;
	}

    /**
     * ZIPファイル内のエントリー一覧を取得する。
     *
     * @param zipFilePath 対象
     * @return 圧縮ファイル内のファイルリスト
     * @throws IOException
     * @note 圧縮ファイルのファイル名エンコードは自動判別を行う。
     */
	public static List<Path> getZipEntries(final Path zipFilePath) throws IOException {
        List<Path> resultList = getZipEntries(zipFilePath, Charset.forName(getZipEncode(zipFilePath)));
        return resultList;
    }

    /**
     * ZIPファイルを、URI形式に変換する。
     *
     * @param zipPath 対象
     * @return 変換されたURI形式
     */
    private static URI createZipUri(final Path zipPath) {
        final String normalizedPath = normalizePathSplit(zipPath.toAbsolutePath().toString());
        if (normalizedPath.startsWith("/")) {
            // Unix
            return URI.create("jar:file:" + normalizedPath);
        } else {
            // Windows
            return URI.create("jar:file:/" + normalizedPath);
        }
    }

    /**
     * パス区切りを、'/'に統一する。
     *
     * @param path 対象文字列
     * @return パス区切り文字を、'/'に統一した文字列
     */
    private static String normalizePathSplit(final String path) {
        return path.toString().replaceAll("\\\\", "/");
    }

    /**
     * ファイルをダウンロードする。
     *
     * @param fileUrl ダウンロードするURL形式の文字列
     * @return ダウンロード(保存)したファイルパス
     * @throws IOException
     */
    public static String download(final String fileUrl) throws IOException {
        HttpURLConnection connection = null;
        String saveFilePath = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            final URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            final int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                final String disposition = connection.getHeaderField("Content-Disposition");
                final String contentType = connection.getContentType();
                final int contentLength = connection.getContentLength();

                if (disposition != null) {
                    final int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
                }

                logger.debug("Content-Type = " + contentType);
                logger.debug("Content-Disposition = " + disposition);
                logger.debug("Content-Length = " + contentLength);
                logger.debug("fileName = " + fileName);

                Path downloadFile = Files.createTempFile("download", null);
                logger.debug("downloadFile = " + downloadFile.toAbsolutePath().toString());

                inputStream = connection.getInputStream();
                saveFilePath = downloadFile.getParent() + File.separator + fileName;
                logger.debug("saveFilePath = " + saveFilePath);

                outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                logger.info("File downloaded. {}", saveFilePath);
            } else {
                logger.error("No file to download. Server replied HTTP code: {}", responseCode);
                saveFilePath = null;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return saveFilePath;
    }
}
