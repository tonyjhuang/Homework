package com.tonyhuangjun.homework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class EditActivityII extends SherlockActivity {

    public static final String ID = "ID";

    // Settings & Editor & System resources.
    private SharedPreferences settings;

    // Information to be displayed.
    private String title;
    private String body;
    private Tile tile;
    private View viewTile;
    private LinearLayout layout;

    // Index of Tile being edited.
    private int index;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // initialize settings and editor.
        settings = getSharedPreferences("Default", MODE_PRIVATE);

        // Get index of class from passed in Intent.
        index = getIntent().getIntExtra(ID, 1);

        // Grab title information from settings.
        title = settings.getString(MainActivityII.CLASS_TITLE + index,
                        "Class Name");
        body = settings.getString(MainActivityII.CLASS_BODY + index,
                        "");

        // Get handler for top layout.
        layout = (LinearLayout) findViewById(R.id.Layout);

        // Instantiate a new tile with title and body information.
        tile = new Tile(getBaseContext(), title, body, index,
                        MainActivityII.EDIT_ID, settings);

        registerForContextMenu(tile.getListView());
    }

    // Called after onResume, orientation change.
    // Will also call after data changes (deletions and insertions) to refresh
    // view.
    protected void onResume() {
        super.onResume();
        // Clear top layout.
        layout.removeAllViews();
        viewTile = tile.getView();
        // Apply layoutparams to viewTile.
        viewTile.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, 1f));
        layout.addView(viewTile);
    }

    private void showAddDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog,
                        null);
        builder.setView(dialogView);
        builder.setTitle("Add Assignment");
        builder.setPositiveButton("Add To Top",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                EditText newTitle = (EditText) dialogView
                                                .findViewById(R.id.AddTitle);
                                EditText newDate = (EditText) dialogView
                                                .findViewById(R.id.AddDate);
                                String name = newTitle.getText()
                                                .toString();
                                if (!name.equals(""))
                                    tile.addToTop(new Assignment(
                                                    name,
                                                    newDate.getText()
                                                                    .toString()));

                            }
                        });
        builder.setNeutralButton("Add To Bottom",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                EditText newTitle = (EditText) dialogView
                                                .findViewById(R.id.AddTitle);
                                EditText newDate = (EditText) dialogView
                                                .findViewById(R.id.AddDate);
                                String name = newTitle.getText()
                                                .toString();
                                if (!name.equals(""))
                                    tile.addToBottom(new Assignment(
                                                    name,
                                                    newDate.getText()
                                                                    .toString()));

                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                            DialogInterface dialog,
                                            int which) {

                            }
                        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // Intercept back button call with dialog. Ask user if he wants to
    // delete unsaved changes.
    @Override
    public void onBackPressed() {
        if (tile.editting) {
            tile.editTitle();
        } else if (!tile.hasChanged()) {
            super.onBackPressed();

        } else
            new AlertDialog.Builder(this)
                            .setTitle("Really Exit?")
                            .setMessage("Delete unsaved changes?")
                            .setNegativeButton(android.R.string.no,
                                            null)
                            .setPositiveButton(android.R.string.yes,
                                            new OnClickListener() {
                                                public void onClick(
                                                                DialogInterface arg0,
                                                                int arg1) {

                                                    EditActivityII.super
                                                                    .onBackPressed();
                                                }
                                            }).create().show();

    }

    // Handle screen rotation.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onResume();
    }

    private void showEditDialog(final int position) {
        Assignment assignment = Interpreter.stringToArrayList2(
                        tile.getBody()).get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog,
                        null);
        final EditText newTitle = (EditText) dialogView
                        .findViewById(R.id.AddTitle);
        final EditText newDate = (EditText) dialogView
                        .findViewById(R.id.AddDate);
        newTitle.setText(assignment.getName());
        newDate.setText(assignment.getDate());
        builder.setView(dialogView);
        builder.setTitle("Edit Assignment");
        builder.setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                tile.edit(new Assignment(
                                                newTitle.getText()
                                                                .toString(),
                                                newDate.getText()
                                                                .toString()),
                                                position);
                                onResume();

                            }
                        });
        builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                            DialogInterface dialog,
                                            int which) {

                            }
                        });
        builder.create().show();

    }

    // ContextMenu for ListViews in Tiles.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.tilecontext, menu);
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                        .getMenuInfo();
        switch (item.getItemId()) {
        case R.id.Edit:
            showEditDialog(info.position);
            onResume();
            return true;
        case R.id.Delete:
            tile.delete(info.position);
            onResume();
            return true;
        default:
            return super.onContextItemSelected((android.view.MenuItem) item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
        case R.id.menu_add:
            showAddDialog();
            break;
        case R.id.menu_save:
            tile.save();
            finish();
            break;
        }
        return true;
    }
}
