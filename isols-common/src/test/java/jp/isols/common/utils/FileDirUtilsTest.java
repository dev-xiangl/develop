package jp.isols.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class FileDirUtilsTest {

    @BeforeEach
    public void beforeEach() {
        Path testTarget = getResourceFile("testDir");
        try {
            FileDirUtils.delete(Paths.get(testTarget.toString(), "copyDir"));
            FileDirUtils.delete(Paths.get(testTarget.toString(), "copyDirFile.txt"));
            FileDirUtils.delete(Paths.get(testTarget.toString(), "moveDir"));
            FileDirUtils.delete(Paths.get(testTarget.toString(), "renameDir"));
            FileDirUtils.delete(Paths.get(testTarget.toString(), "renameDirFile.txt"));
            FileDirUtils.delete(Paths.get(testTarget.toString(), "writeCsvFile.UTF-8.csv"));
            FileDirUtils.delete(Paths.get(testTarget.toString(), "writeCsvFile.Shift_JIS.csv"));
            //FileUtils.delete(Paths.get(testTarget.toString(), "test.zip"));
        } catch (IOException e) {
        }
    }

    private Path getResourceFile(final String path) {
        URI resourceUri = null;
        try {
            resourceUri = getClass().getClassLoader().getResource(path).toURI();
            return Paths.get(resourceUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String getResourceFilePath(final String path) {
        return getResourceFile(path).toString();
    }

    @Test
    public void isExistsTest() throws Exception {
        assertEquals(FileDirUtils.isExists(null), false);
        assertEquals(FileDirUtils.isExists(Paths.get("012")), false);

        Path file = getResourceFile("testFile-A.txt");
        assertEquals(FileDirUtils.isExists(file), true);
        assertEquals(FileDirUtils.isExists(Paths.get(file.toString() + ".error")), false);

        Path dir = getResourceFile("testDir");
        assertEquals(FileDirUtils.isExists(dir.toString(), "testDirFile.txt"), true);
        assertEquals(FileDirUtils.isExists(dir.toString(), "testDirFile.txt.error"), false);

        Path subDir = getResourceFile("testDir/testSubDir");
        assertEquals(FileDirUtils.isExists(subDir.toString(), "testSubDirFile.txt"), true);
        assertEquals(FileDirUtils.isExists(subDir.toString(), "testSubDirFile.txt.error"), false);

        Path emptyDir = getResourceFile("testDir/testEmptySubDir");
        assertEquals(FileDirUtils.isExists(emptyDir.toString(), "testDirFile.txt"), false);
        assertEquals(FileDirUtils.isExists(emptyDir.toString(), "testDirFile.txt.error"), false);
    }

    @Test
    public void copyFileTest() throws Exception {
        Path testTarget = getResourceFile("testDir");

        { /* ファイルをコピー(ディレクトリを指定) */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir/copyDirFile.txt");
            FileDirUtils.copy(copyTarget, getResourceFilePath("testDir"));

            assertEquals(FileDirUtils.isExists(getResourceFile("testDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("copyDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(copyTarget.toString(), "copySubDir/copyDirFile.txt")), false);
        }

        { /* ファイルをコピー(ファイルを指定) */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir/copyDirFile.txt");
            FileDirUtils.copy(copyTarget, Paths.get(testTarget.toString(), "copyDirFile.txt").toAbsolutePath().toString());

            assertEquals(FileDirUtils.isExists(getResourceFile("testDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("copyDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(copyTarget.toString(), "copySubDir/copyDirFile.txt")), false);
        }

        { /* ディレクトリをコピー */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, getResourceFilePath("testDir"));

            assertEquals(FileDirUtils.isExists(getResourceFile("copyDir")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("copyDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("copyDir/copyEmptySubDir")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("copyDir/copySubDir")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("copyDir/copySubDir/copySubDirFile.txt")), true);

            assertEquals(FileDirUtils.isExists(getResourceFile("testDir/copyDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("testDir/copyDir/copyEmptySubDir")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("testDir/copyDir/copySubDir")), true);
            assertEquals(FileDirUtils.isExists(getResourceFile("testDir/copyDir/copySubDir/copySubDirFile.txt")), true);
        }
    }

    @Test
    public void moveFileTest() throws Exception {
        Path testTarget = getResourceFile("testDir");

        { /* ファイルを移動(ディレクトリを指定) */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, testTarget.toString());

            Path moveTarget = getResourceFile("testDir/copyDir/copyDirFile.txt");
            assertEquals(Files.isRegularFile(moveTarget), true);
            FileDirUtils.move(moveTarget, Paths.get(testTarget.toString(), "copyDir").toAbsolutePath().toString());

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/moveDirFile.txt")), false);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "moveDirFile.txt")), false);
        }

        { /* ファイルを移動(ファイルを指定) */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, testTarget.toString());

            Path moveTarget = getResourceFile("testDir/copyDir/copyDirFile.txt");
            assertEquals(Files.isRegularFile(moveTarget), true);
            FileDirUtils.move(moveTarget, Paths.get(testTarget.toString(), "copyDir/moveDirFile.txt").toAbsolutePath().toString());

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/moveDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/copyDirFile.txt")), false);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "moveDirFile.txt")), false);
        }

        { /* ディレクトリを移動 */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, testTarget.toString());

            Path moveTarget = getResourceFile("testDir/copyDir");
            FileDirUtils.move(moveTarget, Paths.get(testTarget.toString(), "moveDir").toAbsolutePath().toString());

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir")), false);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/copyDirFile.txt")), false);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/copyEmptySubDir")), false);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/copySubDir")), false);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/copySubDir/copySubDirFile.txt")), false);

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "moveDir")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "moveDir/copyDir/copyDirFile.txt")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "moveDir/copyDir/copyEmptySubDir")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "moveDir/copyDir/copySubDir")), true);
            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "moveDir/copyDir/copySubDir/copySubDirFile.txt")), true);
        }
    }

    @Test
    public void deleteTest() throws Exception {
        Path testTarget = getResourceFile("testDir");

        { /* ファイルを削除 */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, testTarget.toString());

            Path deleteTarget = getResourceFile("testDir/copyDir/copyDirFile.txt");
            FileDirUtils.delete(deleteTarget);

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/copyDirFile.txt")), false);
        }

        { /* ディレクトリを削除 */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, testTarget.toString());

            Path deleteTarget = getResourceFile("testDir/copyDir");
            FileDirUtils.delete(deleteTarget);

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir")), false);
        }

    }

    @Test
    public void renameTest() throws Exception {
        Path testTarget = getResourceFile("testDir");

        { /* ファイルをリネーム */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, testTarget.toString());

            Path renameTarget = getResourceFile("testDir/copyDir/copyDirFile.txt");
            FileDirUtils.rename(renameTarget, "renameDirFile.txt");

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "copyDir/renameDirFile.txt")), true);
        }

        { /* ディレクトリをリネーム */
            beforeEach();

            Path copyTarget = getResourceFile("copyDir");
            FileDirUtils.copy(copyTarget, testTarget.toString());

            Path renameTarget = getResourceFile("testDir/copyDir");
            FileDirUtils.rename(renameTarget, "renameDir");

            assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "renameDir")), true);
        }
    }

    @Test
    public void readCsvFileTest() throws Exception {
        Path testTarget = getResourceFile("testCsv");

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.UTF-8.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "data1");
                assertEquals(csvList.get(1), "data2");
                assertEquals(csvList.get(2), "data3");
            }
            { /* 2行目 */
                List<String> csvList = csvLineList.get(1);
                assertEquals(csvList.get(0), "あい,うえお");
                assertEquals(csvList.get(1), "かきくけこ");
                assertEquals(csvList.get(2), "さしすせそ");
                assertEquals(csvList.get(3), "たちつてと");
                assertEquals(csvList.get(4), "なにぬねの");
                assertEquals(csvList.get(5), "まみむめも");
                assertEquals(csvList.get(6), "らりるれろ");
            }
        }

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.UTF-8.min.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "あいうえお");
            }
        }

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.UTF-8.singleColumn.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "data1");
            }
            { /* 2行目 */
                List<String> csvList = csvLineList.get(1);
                assertEquals(csvList.get(0), "あいうえお");
            }
        }

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.UTF-8.singleLine.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "あい,うえお");
                assertEquals(csvList.get(1), "かきくけこ");
                assertEquals(csvList.get(2), "さしすせそ");
                assertEquals(csvList.get(3), "たちつてと");
                assertEquals(csvList.get(4), "なにぬねの");
                assertEquals(csvList.get(5), "まみむめも");
                assertEquals(csvList.get(6), "らりるれろ");
            }
        }

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.Shift_JIS.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "data1");
                assertEquals(csvList.get(1), "data2");
                assertEquals(csvList.get(2), "data3");
            }
            { /* 2行目 */
                List<String> csvList = csvLineList.get(1);
                assertEquals(csvList.get(0), "あい,うえお");
                assertEquals(csvList.get(1), "かきくけこ");
                assertEquals(csvList.get(2), "さしすせそ");
                assertEquals(csvList.get(3), "たちつてと");
                assertEquals(csvList.get(4), "なにぬねの");
                assertEquals(csvList.get(5), "まみむめも");
                assertEquals(csvList.get(6), "らりるれろ");
            }
        }

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.Shift_JIS.min.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "あいうえお");
            }
        }

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.Shift_JIS.singleColumn.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "data1");
            }
            { /* 2行目 */
                List<String> csvList = csvLineList.get(1);
                assertEquals(csvList.get(0), "あいうえお");
            }
        }

        {
            beforeEach();

            List<List<String>> csvLineList = FileDirUtils.readCsvFile(Paths.get(testTarget.toString(), "testFile.Shift_JIS.singleLine.csv"));
            { /* 1行目 */
                List<String> csvList = csvLineList.get(0);
                assertEquals(csvList.get(0), "あい,うえお");
                assertEquals(csvList.get(1), "かきくけこ");
                assertEquals(csvList.get(2), "さしすせそ");
                assertEquals(csvList.get(3), "たちつてと");
                assertEquals(csvList.get(4), "なにぬねの");
                assertEquals(csvList.get(5), "まみむめも");
                assertEquals(csvList.get(6), "らりるれろ");
            }
        }
    }

    @Test
    public void writeCsvFileTest() throws Exception {
        Path testTarget = getResourceFile("testCsv");

        {
            beforeEach();

            List<List<String>> csvLineList = new ArrayList<List<String>>();
            { /* 1行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("あい,うえお");
                csvList.add("かきくけこ");
                csvList.add("さしすせそ");
                csvList.add("たちつてと");
                csvList.add("なにぬねの");
                csvList.add("まみむめも");
                csvList.add("らりるれろ");
                csvLineList.add(csvList);
            }
            { /* 2行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("data1");
                csvList.add("data2");
                csvList.add("data3");
                csvList.add("data4");
                csvList.add("data5");
                csvList.add("data6");
                csvList.add("data7");
                csvLineList.add(csvList);
            }

            final Path targetFilePath = Paths.get(testTarget.toString(), "writeCsvFile.UTF-8.csv");
            FileDirUtils.writeCsvFile(targetFilePath, csvLineList);

            assertEquals(FileDirUtils.getCharset(targetFilePath), StringUtils.UTF_8);
            assertEquals(FileDirUtils.getLineNumber(targetFilePath), 2);
        }

        {
            beforeEach();

            List<List<String>> csvLineList = new ArrayList<List<String>>();
            { /* 1行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("あい,うえお");
                csvList.add("かきくけこ");
                csvList.add("さしすせそ");
                csvList.add("たちつてと");
                csvList.add("なにぬねの");
                csvList.add("まみむめも");
                csvList.add("らりるれろ");
                csvLineList.add(csvList);
            }
            { /* 2行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("data1");
                csvList.add("data2");
                csvList.add("data3");
                csvList.add("data4");
                csvList.add("data5");
                csvList.add("data6");
                csvList.add("data7");
                csvLineList.add(csvList);
            }

            final Path targetFilePath = Paths.get(testTarget.toString(), "writeCsvFile.Shift_JIS.csv");
            FileDirUtils.writeCsvFile(targetFilePath, csvLineList, StringUtils.SHIFT_JIS);

            assertEquals(FileDirUtils.getCharset(targetFilePath), StringUtils.SHIFT_JIS);
            assertEquals(FileDirUtils.getLineNumber(targetFilePath), 2);
        }

        {
            beforeEach();

            List<List<String>> csvLineList = new ArrayList<List<String>>();
            { /* 1行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("あい,うえお");
                csvList.add("かきくけこ");
                csvList.add("さしすせそ");
                csvList.add("たちつてと");
                csvList.add("なにぬねの");
                csvList.add("まみむめも");
                csvList.add("らりるれろ");
                csvLineList.add(csvList);
            }
            { /* 2行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("data1");
                csvList.add("data2");
                csvList.add("data3");
                csvList.add("data4");
                csvList.add("data5");
                csvList.add("data6");
                csvList.add("data7");
                csvLineList.add(csvList);
            }

            final Path targetFilePath = Paths.get(testTarget.toString(), "writeCsvFile.UTF-8.csv");
            FileDirUtils.writeCsvFile(targetFilePath, csvLineList);

            { /* 3行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("あい,うえお");
                csvList.add("かきくけこ");
                csvList.add("さしすせそ");
                csvList.add("たちつてと");
                csvList.add("なにぬねの");
                csvList.add("まみむめも");
                csvList.add("らりるれろ");
                csvLineList.add(csvList);
            }
            { /* 4行目 */
                List<String> csvList = new ArrayList<String>();
                csvList.add("data1");
                csvList.add("data2");
                csvList.add("data3");
                csvList.add("data4");
                csvList.add("data5");
                csvList.add("data6");
                csvList.add("data7");
                csvLineList.add(csvList);
            }

            FileDirUtils.writeCsvFile(targetFilePath, csvLineList);

            assertEquals(FileDirUtils.getCharset(targetFilePath), StringUtils.UTF_8);
            assertEquals(FileDirUtils.getLineNumber(targetFilePath), 4);
        }
    }

    @Test
    public void compressToZipTest() throws Exception {
        Path testTarget = getResourceFile("testDir");

        Path[] files = {
            getResourceFile("testFile-A.txt"),
            getResourceFile("testFile-B.txt"),
            getResourceFile("にほんごﾌｧｲﾙ名.txt"),
            getResourceFile("ひらがな.txt"),
            getResourceFile("カタカナ.txt"),
        };

        FileDirUtils.compressToZip(files, testTarget.toAbsolutePath().toString(), "test.zip");
        assertEquals(FileDirUtils.isExists(Paths.get(testTarget.toString(), "test.zip")), true);
    }

    @Test
    public void uncompressToZipTest() throws Exception {
        Path testTarget = getResourceFile("testDir");
        //System.out.println("testTarget: " + testTarget.toAbsolutePath().toString());
        { /* Windowsで生成したZIP */
            Path file = getResourceFile("testDir/testZipWin.zip");

            FileDirUtils.uncompressToZip(file, testTarget.toAbsolutePath().toString(), StringUtils.MS932);
        }
        { /* Macで生成したZIP */
            Path file = getResourceFile("testDir/testZipMac.zip");

            FileDirUtils.uncompressToZip(file, testTarget.toAbsolutePath().toString());
        }
    }

    @Test
    public void getZipEncodeTest() throws Exception {
        { /* Windowsで生成したZIP */
            Path file = getResourceFile("testDir/testZipWin.zip");

            final String encode = FileDirUtils.getZipEncode(file);
            assertEquals(encode, StringUtils.MS932.displayName());
        }
        { /* Windowsで生成したZIP */
            Path file = getResourceFile("testDir/testZipWin-ASCII.zip");

            final String encode = FileDirUtils.getZipEncode(file);
            assertEquals(encode, StringUtils.UTF_8.displayName());
        }
        { /* Macで生成したZIP */
            Path file = getResourceFile("testDir/testZipMac.zip");

            final String encode = FileDirUtils.getZipEncode(file);
            assertEquals(encode, StringUtils.UTF_8.displayName());
        }
        { /* Macで生成したZIP */
            Path file = getResourceFile("testDir/testZipMac-ASCII.zip");

            final String encode = FileDirUtils.getZipEncode(file);
            assertEquals(encode, StringUtils.UTF_8.displayName());
        }
    }

    @Test
    public void getZipEntriesTest() throws Exception {
        final List<String> expected = Arrays.asList(
                "/testFile-A.txt",
                "/testFile-B.txt",
                "/testZipDir/testZipDirFile-A.txt",
                "/testZipDir/testZipDirFile-B.txt",
                "/にほんごﾌｧｲﾙ名.txt"
            );

        { /* Windowsで生成したZIP */
            final List<String> actual = new ArrayList<>(expected);
            assertEquals(actual.size(), expected.size());

            Path file = getResourceFile("testDir/testZipWin.zip");
            List<Path> entries = FileDirUtils.getZipEntries(file, StringUtils.MS932);
            for (final Path path : entries) {
                actual.removeIf(x -> x.equals(path.toAbsolutePath().toString()));
            }
            assertEquals(actual.size(), 0);
        }
        { /* Macで生成したZIP */
            final List<String> actual = new ArrayList<>(expected);
            assertEquals(actual.size(), expected.size());

            Path file = getResourceFile("testDir/testZipMac.zip");
            List<Path> entries = FileDirUtils.getZipEntries(file, StringUtils.UTF_8);
            for (final Path path : entries) {
                actual.removeIf(x -> x.equals(path.toAbsolutePath().toString()));
            }
            assertEquals(actual.size(), 0);
        }
    }

    @Test
    public void downloadTest() throws Exception {
        final String downloadedPath = FileDirUtils.download("https://www.yahoo.co.jp/index.html");
        assertNotNull(downloadedPath);
    }
}
